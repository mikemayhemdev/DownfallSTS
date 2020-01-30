package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.StrengthPower;

import charbosses.actions.unique.EnemyLimitBreakAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnLimitBreak extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Limit Break";
    private static final CardStrings cardStrings;
    
    public EnLimitBreak() {
        super(ID, EnLimitBreak.cardStrings.NAME, "red/skill/limit_break", 1, EnLimitBreak.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyLimitBreakAction());
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnLimitBreak();
    }
    
    @Override
    public int getValue() {
    	if (AbstractCharBoss.boss != null) {
    		int v = 0;
    		if (AbstractCharBoss.boss.hasPower(StrengthPower.POWER_ID)) {
    			v = AbstractCharBoss.boss.getPower(StrengthPower.POWER_ID).amount;
    		}
    		for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
    			if (c.cardID == EnFlex.ID) {
    				v += c.magicNumber;
    			}
    		}
    		return v * 3;
    	}
    	return 20;
    }
    @Override
    public int getPriority() {
    	return 3;
    }
    @Override
    public int getUpgradeValue() {
    	return 12;
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = EnLimitBreak.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Limit Break");
    }
}
