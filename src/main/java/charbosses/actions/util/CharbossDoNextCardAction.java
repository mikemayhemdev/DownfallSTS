package charbosses.actions.util;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CharbossDoNextCardAction extends AbstractGameAction {

    @Override
    public void update() {
        if (AbstractCharBoss.boss != null)
        AbstractCharBoss.boss.makePlay();
        this.isDone = true;
    }

}
