package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.TendrilFlailAction;
import slimebound.patches.AbstractCardEnum;


public class RainOfGoop extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:RainOfGoop";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/rainofgoop.png");
    private static final CardStrings cardStrings;


    public RainOfGoop() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.slimed = this.baseSlimed = 4;
        this.magicNumber = this.baseMagicNumber = 3;
        SlimeboundMod.loadJokeCardImage(this, "RainOfGoop.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TendrilFlailAction(p, null, this.magicNumber, this.slimed));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new RainOfGoop();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


