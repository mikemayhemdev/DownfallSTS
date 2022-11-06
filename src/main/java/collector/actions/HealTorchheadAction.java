package collector.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class HealTorchheadAction extends AbstractGameAction {
    public HealTorchheadAction(int amount) {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        //TODO
        this.isDone = true;
    }
}