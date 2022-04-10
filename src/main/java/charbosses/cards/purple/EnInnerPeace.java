package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.stances.AbstractEnemyStance;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnInnerPeace extends AbstractStanceChangeCard {
    public static final String ID = "downfall_Charboss:InnerPeace";
    private static final CardStrings cardStrings;

    public EnInnerPeace() {
        super(ID, cardStrings.NAME, "purple/skill/inner_peace", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyChangeStanceAction("Calm"));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (AbstractCharBoss.boss.stance instanceof EnCalmStance) return 4;
        return super.getPriority(hand) + 10;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnInnerPeace();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("InnerPeace");
    }

    @Override
    public AbstractEnemyStance changeStanceForIntentCalc(AbstractEnemyStance previousStance) {
        return new EnCalmStance();
    }
}
