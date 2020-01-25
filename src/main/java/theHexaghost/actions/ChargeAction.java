package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ChargeAction extends AbstractGameAction {
    public AbstractGhostflame cuteAnimeGirl;
    public int bruh = 0;

    public ChargeAction(AbstractGhostflame flame) {
        cuteAnimeGirl = flame;
    }

    public ChargeAction(int i) {
        bruh = i;
    }

    public void update() {
        isDone = true;
        if (bruh != 0) {
            int i = GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame);
            i += bruh;
            if (i > GhostflameHelper.hexaGhostFlames.size())
                i = 0;
            cuteAnimeGirl = GhostflameHelper.hexaGhostFlames.get(i);
        }
        cuteAnimeGirl.charge();
    }
}
