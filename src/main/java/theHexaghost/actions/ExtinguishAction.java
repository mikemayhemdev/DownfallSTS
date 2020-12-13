package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ExtinguishAction extends AbstractGameAction {
    public AbstractGhostflame cuteAnimeGirl;
    public int bruh = 0;

    public ExtinguishAction(AbstractGhostflame flame) {
        cuteAnimeGirl = flame;
    }

    public ExtinguishAction(int i) {
        bruh = i;
    }

    public void update() {
     //   if (!HexaMod.renderFlames)
      //      HexaMod.renderFlames = true;
        isDone = true;
        if (bruh != 0) {
            int i = GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame);
            i += bruh;
            if (i >= GhostflameHelper.hexaGhostFlames.size())
                i = 0;
            else if (i == 0) i = GhostflameHelper.hexaGhostFlames.size();
            cuteAnimeGirl = GhostflameHelper.hexaGhostFlames.get(i);
        }
        cuteAnimeGirl.extinguish();
    }
}
