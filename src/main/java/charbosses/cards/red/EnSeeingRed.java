package charbosses.cards.red;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnSeeingRed extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Seeing Red";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Seeing Red");
    }

    public EnSeeingRed() {
        super(ID, EnSeeingRed.cardStrings.NAME, "red/skill/seeing_red", 1, EnSeeingRed.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
        this.energyGeneratedIfPlayed = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyGainEnergyAction(2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSeeingRed();
    }
}
