package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyEnergizedPower;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnOutmaneuver extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Outmaneuver";
    private static final CardStrings cardStrings;
    
    public EnOutmaneuver() {
        super(ID, EnOutmaneuver.cardStrings.NAME, "green/skill/outmaneuver", 1, EnOutmaneuver.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.upgraded) {
            this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergizedPower(m, 2), 2));
        }
        else {
            this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergizedPower(m, 3), 3));
        }
    }
    
    @Override
    public int getValue() {
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
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Outmaneuver");
    }
}
