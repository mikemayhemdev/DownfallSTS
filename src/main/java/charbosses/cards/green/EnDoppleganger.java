package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyDrawCardNextTurnPower;
import charbosses.powers.general.EnemyEnergizedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class EnDoppleganger extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Doppelganger";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Doppelganger");
    }

    public EnDoppleganger() {
        super("Doppelganger", cardStrings.NAME, "green/skill/doppelganger", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergizedPower(m, 2), 2));
        this.addToBot(new ApplyPowerAction(m, m, new EnemyDrawCardNextTurnPower(m, 2), 2));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDoppleganger();
    }
}
