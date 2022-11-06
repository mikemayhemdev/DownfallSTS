package collector.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DrawCardFromCollectionAction extends AbstractGameAction {
    public DrawCardFromCollectionAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        //TODO
        this.isDone = true;
    }
}