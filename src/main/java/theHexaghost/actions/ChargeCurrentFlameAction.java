package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

public class ChargeCurrentFlameAction extends AbstractGameAction {

    public ChargeCurrentFlameAction() {
    }

    public void update() {
//        if (!HexaMod.renderFlames)
//            HexaMod.renderFlames = true;
        isDone = true;
        GhostflameHelper.activeGhostFlame.forceCharge();
    }
}
