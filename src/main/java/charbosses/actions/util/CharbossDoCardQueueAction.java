package charbosses.actions.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import charbosses.bosses.AbstractCharBoss;

public class CharbossDoCardQueueAction extends AbstractGameAction {

	private AbstractCard c;
	
	public CharbossDoCardQueueAction(AbstractCard c) {
		super();
		this.c = c;
	}
	
	@Override
	public void update() {
		AbstractCharBoss.boss.useCard(c, AbstractCharBoss.boss, c.energyOnUse);
		this.isDone = true;
	}

}
