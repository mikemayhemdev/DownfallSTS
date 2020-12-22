package charbosses.cards.red;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnBloodletting extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bloodletting";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bloodletting");
    }

    public EnBloodletting() {
        super(ID, cardStrings.NAME, "red/skill/bloodletting", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.energyGeneratedIfPlayed = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        this.addToBot(new LoseHPAction(m, m, 3));
        this.addToBot(new EnemyGainEnergyAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.energyGeneratedIfPlayed += 1;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBloodletting();
    }
}
