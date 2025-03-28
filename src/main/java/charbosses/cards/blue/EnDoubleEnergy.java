package charbosses.cards.blue;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnDoubleEnergy extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Double Energy";
    private static final CardStrings cardStrings;

    public EnDoubleEnergy() {
        this(2);
    }

    public EnDoubleEnergy(int eGain) {
        super(ID, cardStrings.NAME, "blue/skill/double_energy", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);

        this.baseMagicNumber = eGain;
        this.magicNumber = this.baseMagicNumber;
        this.energyGeneratedIfPlayed = magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m instanceof AbstractCharBoss) {
            addToBot(new EnemyGainEnergyAction(((AbstractCharBoss) m).energyPanel.getCurrentEnergy()));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }

    public AbstractCard makeCopy() {
        return new EnDoubleEnergy();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Double Energy");
    }
}