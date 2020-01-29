package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import charbosses.actions.unique.EnemyArmamentsAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnArmaments extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Armaments";
    private static final CardStrings cardStrings;
    
    public EnArmaments() {
        super(ID, EnArmaments.cardStrings.NAME, "red/skill/armaments", 1, EnArmaments.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 5;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyArmamentsAction(this.upgraded));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = EnArmaments.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    private int getUpgradeoValue() {
    	if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractCharBoss.boss != null) {
	    	int value = 0;
	    	for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
	    		if (c.canUpgrade() && c != this) {
	    			int deltavalue;
	    			if (c.cardID == this.cardID) {
	    				deltavalue = 9;
	    			} else {
	    				deltavalue = ((AbstractBossCard)c).getUpgradeValue();
	    			}
	        		if (this.upgraded) {
	        			value += deltavalue;
	        		} else if (deltavalue > value) {
	        			value = deltavalue;
	        		}
	    		}
	    	}
	    	return value;
    	}
    	return (this.upgraded ? 3 : 9);
    }
    @Override
    public int getValue() {
		return super.getValue() + this.getUpgradeoValue();
	}
    @Override
    public int getPriority() {
		return 5;
	}
    
    @Override
    public AbstractCard makeCopy() {
        return new EnArmaments();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Armaments");
    }
}
