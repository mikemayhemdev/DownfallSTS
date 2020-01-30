package charbosses.cards;

import java.util.ArrayList;

import charbosses.relics.AbstractCharbossRelic;

public abstract class AbstractBossDeckArchetype {
	public String ID;
	public ArrayList<AbstractBossCard> allCards;
	public ArrayList<AbstractCharbossRelic> synergyRelics;
	public AbstractBossDeckArchetype(String id) {
		this.ID = id;
		this.allCards = new ArrayList<AbstractBossCard>();
		this.synergyRelics = new ArrayList<AbstractCharbossRelic>();
	}
	
	public abstract ArrayList<AbstractBossCard> buildCardList();
}
