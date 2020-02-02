package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ExtinguishCurrentFlameAction extends AbstractGameAction {

    public ExtinguishCurrentFlameAction() {
    }

    public void update() {
        if (!HexaMod.renderFlames)
            HexaMod.renderFlames = true;
        isDone = true;
        GhostflameHelper.activeGhostFlame.extinguish();
    }
}
