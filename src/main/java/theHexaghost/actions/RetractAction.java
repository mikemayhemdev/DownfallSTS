package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;

public class RetractAction extends AbstractGameAction {

    public RetractAction() {
    }

    public void update() {
        isDone = true;
        //  if (!HexaMod.renderFlames)
        //      HexaMod.renderFlames = true;
        GhostflameHelper.retract();
    }
}
