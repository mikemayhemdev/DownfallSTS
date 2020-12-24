package automaton.cards.goodstatus;


import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

@CardIgnore
public class UnknownStatus extends AbstractCard {
    public static final String ID = "bronze:UnknownStatus";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "sneckomodResources/images/cards/PerplexingSnare";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


    public UnknownStatus() {

        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        block = baseBlock = 8;

        tags.add(AutomatonMod.GOOD_STATUS);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, block));
    }

    public AbstractCard makeCopy() {
        return new UnknownStatus();
    }

    public void upgrade() {
        upgradeBlock(3);
    }

}

