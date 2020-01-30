package charbosses.actions.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import charbosses.bosses.AbstractCharBoss;

public class CharbossDoNextCardAction extends AbstractGameAction {

	@Override
	public void update() {
		AbstractCharBoss.boss.makePlay();
		this.isDone = true;
	}

}
