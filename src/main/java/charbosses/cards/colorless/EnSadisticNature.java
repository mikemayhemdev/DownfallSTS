package charbosses.cards.colorless;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.SadisticPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.ArrayList;

public class EnSadisticNature extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Sadistic Nature";
    private static final CardStrings cardStrings;


    public EnSadisticNature() {
        super(ID, cardStrings.NAME, "colorless/power/sadistic_nature", 0, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new SadisticPower(m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 15;
    }

    public AbstractCard makeCopy() {
        return new EnSadisticNature();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Sadistic Nature");
    }
}

