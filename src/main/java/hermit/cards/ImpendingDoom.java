package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.HermitMod;
import hermit.powers.Concentration;
import hermit.powers.SnipePower;
import hermit.util.Wiz;


import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class ImpendingDoom extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(ImpendingDoom.class.getSimpleName());
    public static final String IMG = makeCardPath("impending_doom.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.CURSE; // remove special rarity to become downfall curse
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = -2;

    private static final int DAMAGE = 13;

    // /STAT DECLARATION/

    public ImpendingDoom() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        loadJokeCardImage(this, "impending_doom.png");

        //Impending Doom can now show up as a Curse during Downfall Mode runs.
        tags.add(downfallMod.DOWNFALL_CURSE);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            Wiz.atb(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, baseDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            Wiz.atb(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(baseDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            CardCrawlGame.sound.playA("BELL", -0.5f);
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = false;

        // I decree that Impending Doom is not a Dead On effect itself.
        // The alternative is too horrible to imagine.
        double hand_pos = (AbstractDungeon.player.hand.group.indexOf(this)+0.5);
        double hand_size = (AbstractDungeon.player.hand.size());
        double relative = Math.abs(hand_pos-hand_size/2);

        if (relative<1)
        {
            dontTriggerOnUseCard = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();

        double hand_pos = (AbstractDungeon.player.hand.group.indexOf(this)+0.5);
        double hand_size = (AbstractDungeon.player.hand.size());
        double relative = Math.abs(hand_pos-hand_size/2);

        if (relative<1)
        {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
    }
}