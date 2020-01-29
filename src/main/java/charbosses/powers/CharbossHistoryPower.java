package charbosses.powers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.CharBossIronclad;
import charbosses.cards.colorless.EnBite;

public class CharbossHistoryPower extends AbstractPower {
	public static final String POWER_ID = "EvilWithin:History";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public static abstract class EventItem {
    	public String name;
    	public String desc;
    	public EventItem(String n, String d) {
    		this.name = n;
    		this.desc = d;
    	}
    	
    	public abstract void onApply(AbstractCharBoss boss);
    	
    	//this checks if a specific archetype should have encountered this event. return 0 if it shouldn't, 1 for normal, and 2+ for higher weight.
    	public int friendlyToArchetype(String a) {
    		return 1;
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
    
    public static class EI_Vampires extends EventItem {
		public EI_Vampires() {
			super(CharbossHistoryPower.DESCRIPTIONS[0], CharbossHistoryPower.DESCRIPTIONS[1]);
		}

		@Override
		public void onApply(AbstractCharBoss boss) {
			final ArrayList<AbstractCard> masterDeck = boss.masterDeck.group;
	        for (int i = masterDeck.size() - 1; i >= 0; --i) {
	            final AbstractCard card = masterDeck.get(i);
	            if (card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
	                boss.masterDeck.removeCard(card);
	            }
	        }
	        for (int i = 0; i < 5; ++i) {
	            boss.masterDeck.addToTop(new EnBite());
	        }
		}
		
		@Override
		public int friendlyToArchetype(String a) {
			if (a.equals(CharBossIronclad.ARCHETYPE_IC_STRIKE)) {
				return 0;
			}
    		return 1;
    	}
    }
}
