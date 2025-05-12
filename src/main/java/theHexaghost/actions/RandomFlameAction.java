package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;

public class RandomFlameAction extends AbstractGameAction {

    public RandomFlameAction(){}

    public void update() {
        isDone = true;
        GhostflameHelper.end_on_random_flame();
    }
}
