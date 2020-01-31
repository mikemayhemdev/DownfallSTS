package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class zzzMaxSlimes extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzMaxSlimes";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/makesomeroom.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
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

    public zzzMaxSlimes() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction(this.magicNumber));

    }

    public AbstractCard makeCopy() {
        return new zzzMaxSlimes();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            // this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();


        }
    }
}

