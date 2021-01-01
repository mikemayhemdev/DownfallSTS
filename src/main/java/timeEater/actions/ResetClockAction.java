package timeEater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import timeEater.ClockHelper;

public class ResetClockAction extends AbstractGameAction {
    @Override
    public void update() {
        isDone = true;
        ClockHelper.clock = 1;
    }
}
