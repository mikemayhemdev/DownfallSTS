package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnAccuracy extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Accuracy";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Accuracy");
    }

    public EnAccuracy() {
        super(ID, EnAccuracy.cardStrings.NAME, "green/power/accuracy", 1, EnAccuracy.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Shiv();
        this.magicValue = 5;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyAccuracyPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnAccuracy();
    }
}
