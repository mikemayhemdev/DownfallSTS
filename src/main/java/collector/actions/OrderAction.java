package collector.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class OrderAction extends AbstractGameAction {
    public OrderAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        //TODO
        this.isDone = true;
    }
}