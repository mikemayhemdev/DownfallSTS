package collector.actions;

import collector.util.NewReserves;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GainReservesAction extends AbstractGameAction {
    public GainReservesAction(int total) {
        this.actionType = ActionType.SPECIAL;
        this.amount = total;
    }

    @Override
    public void update() {
        NewReserves.addReserves(amount);
        this.isDone = true;
    }
}