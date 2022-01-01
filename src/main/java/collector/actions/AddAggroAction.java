package collector.actions;

import collector.CollectorChar;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class AddAggroAction extends AbstractGameAction {
    boolean isDragon;

    public AddAggroAction(int delta) {
        actionType = ActionType.WAIT;
        duration = Settings.ACTION_DUR_FAST;
        amount = delta;
    }

    public void update() {
        isDone = true;
        if (CollectorChar.getLivingTorchHead() == null){
            return;
        }
        if (FreezeAggroAction.frozen) {
            return;
        }
        CollectorMod.TorchAggro += amount;
    }
}