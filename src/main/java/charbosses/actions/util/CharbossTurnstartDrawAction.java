package charbosses.actions.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import charbosses.bosses.AbstractCharBoss;

public class CharbossTurnstartDrawAction extends AbstractGameAction {

	@Override
	public void update() {
		AbstractCharBoss.boss.endTurnStartTurn();
		this.isDone = true;
	}

}
