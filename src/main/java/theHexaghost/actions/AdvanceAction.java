package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;

public class AdvanceAction extends AbstractGameAction {

    public AdvanceAction() {
    }

    public void update() {
        isDone = true;
        GhostflameHelper.advance();
    }
}
