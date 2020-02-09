package charbosses.actions.util;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CharbossTurnstartDrawAction extends AbstractGameAction {

    @Override
    public void update() {
        AbstractCharBoss.boss.endTurnStartTurn();
        this.isDone = true;
    }

}
