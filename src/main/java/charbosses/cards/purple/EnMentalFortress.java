package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyMasterRealityPower;
import charbosses.powers.cardpowers.EnemyMentalFortressPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnMentalFortress extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:MentalFortress";
    private static final CardStrings cardStrings;

    public EnMentalFortress() {
        super(ID, cardStrings.NAME, "purple/power/mental_fortress", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyMentalFortressPower(m, magicNumber), magicNumber));
    }

    public AbstractCard makeCopy() {
        return new EnMentalFortress();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MentalFortress");
    }
}
