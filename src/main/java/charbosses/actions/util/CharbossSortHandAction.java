package charbosses.actions.util;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CharbossSortHandAction extends AbstractGameAction {

    @Override
    public void update() {
        AbstractCharBoss.boss.sortHand();
        this.isDone = true;
    }

}
