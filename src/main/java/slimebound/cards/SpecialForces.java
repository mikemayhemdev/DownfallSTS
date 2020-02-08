package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class SpecialForces extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SpecialForces";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/acidgelatin.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SpecialForces() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = new SplitBronze();
        if (upgraded) q.upgrade();
        addToBot(new MakeTempCardInHandAction(q));
        AbstractCard r = new SplitGhostflame();
        if (upgraded) r.upgrade();
        addToBot(new MakeTempCardInHandAction(r));
        AbstractCard s = new SplitTorchHead();
        if (upgraded) s.upgrade();
        addToBot(new MakeTempCardInHandAction(s));
        AbstractCard z = new SplitCultist();
        if (upgraded) z.upgrade();
        addToBot(new MakeTempCardInHandAction(z));
    }

    public AbstractCard makeCopy() {
        return new SpecialForces();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}

