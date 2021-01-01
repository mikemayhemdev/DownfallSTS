package timeEater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import timeEater.ClockHelper;

public class TickAction extends AbstractGameAction {
    @Override
    public void update() {
        isDone = true;
        ClockHelper.advance();
    }
}
