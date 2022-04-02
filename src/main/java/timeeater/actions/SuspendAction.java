package timeeater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import timeeater.suspend.SuspendHelper;

public class SuspendAction extends AbstractGameAction {
    private AbstractCard target;

    public SuspendAction(AbstractCard target) {
        this.target = target;
    }

    @Override
    public void update() {
        isDone = true;
        SuspendHelper.suspend(target);
    }
}
