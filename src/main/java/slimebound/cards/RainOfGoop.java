package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.TendrilFlailAction;
import slimebound.patches.AbstractCardEnum;


public class RainOfGoop extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:RainOfGoop";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/rainofgoop.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public RainOfGoop() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 0;
        this.slimed = this.baseSlimed = 3;
        this.magicNumber = this.baseMagicNumber = 4;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new TendrilFlailAction(p,

                AbstractDungeon.getMonsters().getRandomMonster(true), this.magicNumber, this.slimed));

    }

    public AbstractCard makeCopy() {

        return new RainOfGoop();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            this.upgradeMagicNumber(2);
        }

    }
}


