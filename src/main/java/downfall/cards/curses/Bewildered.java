package downfall.cards.curses;


import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.cardmods.PropertiesMod;
import hermit.util.Wiz;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.MuddleHandAction;
import sneckomod.cards.AbstractSneckoCard;
import sneckomod.powers.MuddleDrawnCardsPower;


public class Bewildered extends CustomCard {
    public static final String ID = downfallMod.makeID("Bewildered");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/bewildered.png");
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 4;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    private boolean activateThisTurn = false;

    public Bewildered() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);
//        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
       // this.isEthereal = true;
        tags.add(downfallMod.DOWNFALL_CURSE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }


    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        this.flash();
        Wiz.atb(new MuddleAction(this));
    }

    public AbstractCard makeCopy() {
        return new Bewildered();
    }

    public void upgrade() {
    }
}


