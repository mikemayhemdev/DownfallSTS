package slimebound.cards;


import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class Conspire extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Conspire";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/hauntinglick.png");
    private static final CardStrings cardStrings;

    public Conspire() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        SlimeboundMod.loadJokeCardImage(this, "hauntinglick.png");

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DrawCardAction(1));
        Wiz.applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new Conspire();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


