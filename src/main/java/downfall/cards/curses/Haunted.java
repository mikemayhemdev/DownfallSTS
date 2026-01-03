package downfall.cards.curses;


import automaton.cards.goodstatus.IntoTheVoid;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import collector.cards.OnOtherCardExhaustInHand;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.cardmods.PropertiesMod;
import sneckomod.cards.TyphoonFang;


public class Haunted extends CustomCard {
    public static final String ID = downfallMod.makeID("Haunted");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/haunted.png");
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Haunted() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 6;

        this.isEthereal = true;
        tags.add(downfallMod.DOWNFALL_CURSE);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
        }

    }

//    public void triggerOnEndOfTurnForPlayingCard() {
//        this.dontTriggerOnUseCard = true;
//        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
//    }

//    public void triggerOnExhaust() {
//        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
//
//    }

//    @Override
//    public void atTurnStart() {
//    }

    /*
    @Override
    public void onOtherCardExhaustWhileInHand(AbstractCard card) {
        if (!AbstractDungeon.actionManager.turnHasEnded) {
            if (card != this) {
                flash(Color.PURPLE.cpy());
                this.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

     */


    public void triggerOnEndOfTurnForPlayingCard() {
        flash(Color.PURPLE.cpy());
        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
    }




    public AbstractCard makeCopy() {
        return new Haunted();
    }

    public void upgrade() {
    }
}


