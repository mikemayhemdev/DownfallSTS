package slimebound.cards;



import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.ExhumeToDrawAction;
import slimebound.actions.ReturnRandom0Cost;
import slimebound.patches.AbstractCardEnum;


public class Recollect extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Recollect";
    public static final String NAME;
    private static final CardStrings cardStrings;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/absorball.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private int numEaten = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;


    public Recollect() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseBlock = 8;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.exhaust = true;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ReturnRandom0Cost(this.magicNumber));


    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public AbstractCard makeCopy() {
        return new Recollect();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            //upgradeMagicNumber(1);
           // this.rawDescription = UPGRADED_DESCRIPTION;
            //this.initializeDescription();


        }
    }
}

