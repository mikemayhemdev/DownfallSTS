package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemySpotWeaknessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class EnSpotWeakness extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:SpotWeakness";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Spot Weakness");
    }

    public EnSpotWeakness() {
        super(ID, EnSpotWeakness.cardStrings.NAME, "red/skill/spot_weakness", 1, EnSpotWeakness.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
//        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
        if (m.hasPower(EnemySpotWeaknessPower.POWER_ID)) {
            EnemySpotWeaknessPower power = (EnemySpotWeaknessPower) m.getPower(EnemySpotWeaknessPower.POWER_ID);
            this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, power.amount), power.amount));
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return super.getPriority(hand) + 10;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSpotWeakness();
    }
}
