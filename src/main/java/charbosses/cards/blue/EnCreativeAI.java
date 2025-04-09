
package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyCreativeAIPower;
import charbosses.powers.general.EnemyDrawPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.MachineLearning;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;

import java.util.ArrayList;

public class EnCreativeAI extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:CreativeAI";
    private static final CardStrings cardStrings;

    public EnCreativeAI() {
        super(ID, cardStrings.NAME, "blue/power/creative_ai", 3, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        alwaysDisplayText = true;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyCreativeAIPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new EnMachineLearning();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Creative AI");
    }
}
