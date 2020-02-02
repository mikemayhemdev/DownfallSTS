package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

public class AdvanceAction extends AbstractGameAction {

    public AdvanceAction() {
    }

    public void update() {
        isDone = true;
        if (!HexaMod.renderFlames)
            HexaMod.renderFlames = true;
        GhostflameHelper.advance();
    }
}
