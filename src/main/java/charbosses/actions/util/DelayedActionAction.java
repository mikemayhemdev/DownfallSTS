package charbosses.actions.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayedActionAction extends AbstractGameAction {
	AbstractGameAction act;
	
	public DelayedActionAction(AbstractGameAction a) {
		this.act = a;
	}
	
	@Override
	public void update() {
		this.addToBot(this.act);
		this.isDone = true;
	}

}
