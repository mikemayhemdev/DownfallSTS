package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.InfernoGhostflame;

public class AdvanceUntilInfernoAction extends AbstractGameAction {

    public AdvanceUntilInfernoAction() {
    }

    public void update() {
        isDone = true;
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (!(GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame))
                    addToTop(new AdvanceUntilInfernoAction());
            }
        });
        addToTop(new AdvanceAction());
    }
}
