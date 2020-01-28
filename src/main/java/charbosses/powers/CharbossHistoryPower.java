package charbosses.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CharbossHistoryPower extends AbstractPower {
	public static final String POWER_ID = "EvilWithin:History";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public static class EventItem {
    	public String name;
    	public String desc;
    	public EventItem(String n, String d) {
    		this.name = n;
    		this.desc = d;
    	}
    }
    
    public CharbossHistoryPower(final AbstractCreature owner) {
        this.name = CharbossHistoryPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.description = CharbossHistoryPower.DESCRIPTIONS[0];
        this.loadRegion("phantasmal");
        this.isTurnBased = false;
    }
    
    @Override
    public void updateDescription() {
    	this.description = CharbossHistoryPower.DESCRIPTIONS[0];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = CharbossHistoryPower.powerStrings.NAME;
        DESCRIPTIONS = CharbossHistoryPower.powerStrings.DESCRIPTIONS;
    }
}
