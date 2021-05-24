package collector.actions;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class AddAggroAction extends AbstractGameAction {
    boolean isDragon;

    public AddAggroAction(boolean isDragon, int delta) {
        actionType = ActionType.WAIT;
        duration = Settings.ACTION_DUR_FAST;
        this.isDragon = isDragon;
        amount = delta;
    }

    public void update() {
        isDone = true;
        if (FreezeAggroAction.frozen) {
            return;
        }

        if (isDragon) {
            CollectorMod.TorchAggro += amount;
        } else {
            CollectorMod.CollectorAggro += amount;
        }
    }
}