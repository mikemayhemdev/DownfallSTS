package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ExtinguishCurrentFlameAction extends AbstractGameAction {

    public ExtinguishCurrentFlameAction() {
    }

    public void update() {
        isDone = true;
        GhostflameHelper.activeGhostFlame.extinguish();
    }
}
