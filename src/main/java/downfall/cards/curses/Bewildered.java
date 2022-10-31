package downfall.cards.curses;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;
import sneckomod.cards.AbstractSneckoCard;


public class Bewildered extends CustomCard {
    public static final String ID = downfallMod.makeID("Bewildered");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/bewildered.png");
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
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

        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        tags.add(downfallMod.DOWNFALL_CURSE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        AbstractDungeon.actionManager.addToBottom(new MuddleHandAction());
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
    }

    public AbstractCard makeCopy() {
        return new Bewildered();
    }

    public void upgrade() {
    }
}


