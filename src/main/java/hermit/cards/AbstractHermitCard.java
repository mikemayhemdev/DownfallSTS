package hermit.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hermit.actions.ComboAction;
import hermit.patches.EndOfTurnPatch;
import hermit.patches.VigorPatch;
import hermit.powers.BigShotPower;
import hermit.powers.ComboPower;
import hermit.powers.Concentration;
import hermit.powers.SnipePower;
import hermit.relics.BlackPowder;
import hermit.util.TextureLoader;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractHermitCard extends CustomCard {

    static ArrayList<AbstractCard> allCards = new ArrayList();

    static boolean doneInit = false;
    public boolean trig_deadon = false;
    public int trig_times = 1;

    public static void init() {
        doneInit=true;
    }

    public int defaultSecondMagicNumber;
    public int defaultBaseSecondMagicNumber;
    public boolean upgradedDefaultSecondMagicNumber;
    public boolean isDefaultSecondMagicNumberModified;
    public static boolean lastCardDeadOn = false;
    public static ArrayList<Boolean> deadOnThisTurn = new ArrayList<>();

    public AbstractHermitCard(final String id,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) {
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
            isDefaultSecondMagicNumberModified = true;
        }

    }

    public void upgradeDefaultSecondMagicNumber(int amount) {
        defaultBaseSecondMagicNumber += amount;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        upgradedDefaultSecondMagicNumber = true;
    }

    @Override
    public void resetAttributes()
    {
        super.resetAttributes();

        this.trig_deadon = false;
        this.trig_times = 1;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard original = super.makeStatEquivalentCopy();

        ((AbstractHermitCard)original).trig_deadon = this.trig_deadon;

        return original;
    }

    public boolean isDeadOn()
    {
        boolean conc = AbstractDungeon.player.hasPower(Concentration.POWER_ID);

        double hand_pos = (AbstractDungeon.player.hand.group.indexOf(this)+0.5);
        double hand_size = (AbstractDungeon.player.hand.size());
        double relative = Math.abs(hand_pos-hand_size/2);

        if (relative<1 || conc)
        {
            this.trig_deadon = true;
        }

        return (trig_deadon);
    }

    public boolean isDeadOnPos()
    {
        boolean conc = AbstractDungeon.player.hasPower(Concentration.POWER_ID);

        double hand_pos = (AbstractDungeon.player.hand.group.indexOf(this)+0.5);
        double hand_size = (AbstractDungeon.player.hand.size());
        double relative = Math.abs(hand_pos-hand_size/2);

        return (relative<1 || conc);
    }

    protected boolean onDeadOn()
    {
        int DeadOnTimes = DeadOnAmount();

        for (int a = 0; a < DeadOnTimes; a++) {
            if (AbstractDungeon.player.hasPower(BigShotPower.POWER_ID)) {
                VigorPatch.isActive += 1;
            }

            if (!dontTriggerOnUseCard) {
                if (deadOnThisTurn.size() > 0)
                    AbstractHermitCard.deadOnThisTurn.set(deadOnThisTurn.size() - 1, true);
                EndOfTurnPatch.deadon_counter++;
            }
        }

        if (AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            ComboPower comb = (ComboPower)AbstractDungeon.player.getPower(ComboPower.POWER_ID);

            if (comb.uses < comb.amount)
            {
                comb.uses++;

                if (!doneInit)
                    init();

                comb.flash();

                this.addToBot(new ComboAction(false, this));
            }
        }

        if (AbstractDungeon.player.hasPower(SnipePower.POWER_ID))
        AbstractDungeon.player.getPower(SnipePower.POWER_ID).flash();

        return true;
    }

    public void TriggerDeadOnEffect(AbstractPlayer p, AbstractMonster m)
    {
        // Things that happen before Dead On effect.
        onDeadOn();

        // Trigger Dead On effect.
        int DeadOnTimes = DeadOnAmount();

        // For effects that are stacked, rather than repeated.
        DeadOnEffectStacking(p,m,DeadOnTimes);

        // Effects that are repeated.
        for (int a = 0; a < DeadOnTimes; a++) {
            DeadOnEffect(p,m);

            // Trigger Black Powder.
            Iterator findPow = AbstractDungeon.player.relics.iterator();

            while(findPow.hasNext()) {
                AbstractRelic c = (AbstractRelic)findPow.next();

                if (c.relicId.equals(BlackPowder.ID))
                {
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, c));
                    this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(BlackPowder.OOMPH, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE,true));
                }
            }
        }

        // Remove Concentration.
        this.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SnipePower.POWER_ID, 1));
    }

    public void DeadOnEffect(AbstractPlayer p, AbstractMonster m)
    {
    }

    public void DeadOnEffectStacking(AbstractPlayer p, AbstractMonster m, int val)
    {
    }

    public int DeadOnAmount()
    {
        int do_times = trig_times;

        if (AbstractDungeon.player.hasPower(SnipePower.POWER_ID)) {
            do_times++;
        }

        return do_times;
    }

    public String betaArtPath;
    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    newPath = "hermitResources/images/betacards/" + newPath;
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    public static class Enums {
        @SpireEnum
        public static AbstractCard.CardTags DEADON;
    }


}