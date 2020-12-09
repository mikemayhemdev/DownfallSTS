package champ.cards;

import basemod.abstracts.CustomCard;
import champ.ChampChar;
import champ.actions.FatigueHpLossAction;
import champ.powers.CalledShotPower;
import champ.powers.ResolvePower;
import champ.stances.*;
import champ.util.OnOpenerSubscriber;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

import static champ.ChampMod.getModID;
import static champ.ChampMod.makeCardPath;


public abstract class AbstractChampCard extends CustomCard {

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected String DESCRIPTION;
    protected String UPGRADE_DESCRIPTION;
    protected String[] EXTENDED_DESCRIPTION;

    public int cool;
    public int baseCool;
    public boolean upgradedCool;
    public boolean isCoolModified;

    public int myHpLossCost;

    public void resetAttributes() {
        super.resetAttributes();
        cool = baseCool;
        isCoolModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedCool) {
            cool = baseCool;
            isCoolModified = true;
        }
    }

    void upgradeCool(int amount) {
        baseCool += amount;
        cool = baseCool;
        upgradedCool = true;
    }


    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, ChampChar.Enums.CHAMP_GRAY, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public static String getCorrectPlaceholderImage(CardType type, String id) {
        String img = makeCardPath(id.replaceAll((getModID() + ":"), "") + ".png");
        if ((!Gdx.files.internal(img).exists()))
            switch (type) {
                case ATTACK:
                    return makeCardPath("Attack.png");
                case SKILL:
                    return makeCardPath("Skill.png");
                case POWER:
                    return makeCardPath("Power.png");
            }
        return img;
    }

    public static String makeID(String blah) {
        return getModID() + ":" + blah;
    }

    public static boolean gcombo() {
        return (AbstractDungeon.player.stance.ID.equals(GladiatorStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public static boolean bcombo() {
        return (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public static boolean dcombo() {
        return (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public int fatigue(int amount) {
        int x = Math.min(amount, AbstractDungeon.player.currentHealth - 1);
        int y = AbstractDungeon.player.currentHealth;
        atb(new FatigueHpLossAction(AbstractDungeon.player, AbstractDungeon.player, x));
        /*atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (y - AbstractDungeon.player.currentHealth > 0) {
                    att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(y - AbstractDungeon.player.currentHealth), y - AbstractDungeon.player.currentHealth));
                }
            }
        });
        */ //This unused method makes it so the player only gains Resolve equal to lost HP. Fixes some breakable things, but also unfun.
        applyToSelf(new ResolvePower(x));
        return x;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public abstract void upp();

    protected void atb(AbstractGameAction action) {
        addToBot(action);
    }

    protected void att(AbstractGameAction action) {
        addToTop(action);
    }

    protected DamageInfo makeInfo() {
        return makeInfo(damageTypeForTurn);
    }

    private DamageInfo makeInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, type);
    }

    public void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, makeInfo(), fx));
    }

    public void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    private void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public ArrayList<AbstractMonster> monsterList() {
        ArrayList<AbstractMonster> q = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDying && !m.isDead) q.add(m);
        }
        return q;
    }

    public void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    WeakPower autoWeak(AbstractMonster m, int i) {
        return new WeakPower(m, i, false);
    }

    VulnerablePower autoVuln(AbstractMonster m, int i) {
        return new VulnerablePower(m, i, false);
    }

    public void triggerOpenerRelics(boolean fromNeutral) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnOpenerSubscriber) ((OnOpenerSubscriber) r).onOpener(fromNeutral);
        }
    }

    public void berserkOpen() {
        berserkerStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public void gladOpen() {
        gladiatorStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public void defenseOpen() {
        defensiveStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    protected void berserkerStance() {
        SlimeboundMod.logger.info("Switching to Berserker (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(BerserkerStance.STANCE_ID));
    }

    protected void gladiatorStance() {
        SlimeboundMod.logger.info("Switching to Gladiator (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(GladiatorStance.STANCE_ID));
    }

    protected void defensiveStance() {
        SlimeboundMod.logger.info("Switching to Defensive (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(DefensiveStance.STANCE_ID));
    }

    protected void ultimateStance() {
        SlimeboundMod.logger.info("Switching to THE ULTIMATE STANCE!!! (Abstract)");
        atb(new ChangeStanceAction(UltimateStance.STANCE_ID));
    }

    public void exitStance() {
        SlimeboundMod.logger.info("Switching to Neutral (Abstract)");
        atb(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    public void techique() {
        if (AbstractDungeon.player.stance instanceof AbstractChampStance)
            ((AbstractChampStance) AbstractDungeon.player.stance).techique();
    }

    public void finisher() {
        if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
            if (!AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID) && !(AbstractDungeon.player.stance instanceof UltimateStance)) {
                exitStance();
            }
            ((AbstractChampStance) AbstractDungeon.player.stance).fisher();
            if (AbstractDungeon.player.stance instanceof UltimateStance){

            }
            else if (AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID)) {
                AbstractDungeon.player.getPower(CalledShotPower.POWER_ID).onSpecificTrigger();
            } else
                addToBot(new PressEndTurnButtonAction());
        }
    }
}