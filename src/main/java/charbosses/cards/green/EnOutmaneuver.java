package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyEnergizedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnOutmaneuver extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Outmaneuver";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Outmaneuver");
    }

    public EnOutmaneuver() {
        super(ID, EnOutmaneuver.cardStrings.NAME, "green/skill/outmaneuver", 1, EnOutmaneuver.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.NONE, AbstractMonster.Intent.BUFF);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.upgraded) {
            this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergizedPower(m, 2), 2));
        } else {
            this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergizedPower(m, 3), 3));
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return (this.upgraded ? 14 : 7);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = EnOutmaneuver.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnOutmaneuver();
    }
}
