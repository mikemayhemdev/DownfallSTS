package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;

public class ChargeCurrentFlameAction extends AbstractGameAction {

    public ChargeCurrentFlameAction() {
    }

    public void update() {
        isDone = true;
        GhostflameHelper.activeGhostFlame.charge();
    }
}
