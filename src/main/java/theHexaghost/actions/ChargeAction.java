package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ChargeAction extends AbstractGameAction {
    public AbstractGhostflame workingFlame;
    public int bruh = 0;

    public ChargeAction(AbstractGhostflame flame) {
        workingFlame = flame;
    }

    public ChargeAction(int i) {
        bruh = i;
    }

    public void update() {
        //  if (!HexaMod.renderFlames)
        //       HexaMod.renderFlames = true;
        isDone = true;
        if (bruh != 0) {
            int i = GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame);
            i += bruh;
            if (i >= GhostflameHelper.hexaGhostFlames.size())
                i %= GhostflameHelper.hexaGhostFlames.size();
            else if (i == 0) i = GhostflameHelper.hexaGhostFlames.size();
            workingFlame = GhostflameHelper.hexaGhostFlames.get(i);
        }
        workingFlame.charge();
    }
}
