package charbosses.actions.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import charbosses.bosses.AbstractCharBoss;

public class CharbossSortHandAction extends AbstractGameAction {

	@Override
	public void update() {
		AbstractCharBoss.boss.sortHand();
		this.isDone = true;
	}

}
