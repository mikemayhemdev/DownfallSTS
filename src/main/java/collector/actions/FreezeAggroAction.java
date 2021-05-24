package collector.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class FreezeAggroAction extends AbstractGameAction {
    public static boolean frozen = false;

    public boolean freeze;

    public FreezeAggroAction(boolean freeze) {
        actionType = ActionType.WAIT;
        duration = Settings.ACTION_DUR_FAST;
        this.freeze = freeze;
    }

    public void update() {
        this.isDone = true;

        frozen = freeze;
    }
}
