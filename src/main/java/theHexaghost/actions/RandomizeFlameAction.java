package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

public class RandomizeFlameAction extends AbstractGameAction {
    public AbstractGhostflame flameToRandomize;
    public int bruh = 0;


    public RandomizeFlameAction(int i) {
        bruh = i;
    }

    public void update() {

                AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(0);

                if (bruh == 0) {
                    gf = new SearingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                }

                if (bruh == 1) {
                    gf = new CrushingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                           }

                if (bruh == 2) {
                    gf = new BolsteringGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                }

                GhostflameHelper.hexaGhostFlames.set((GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame)), gf);
                gf.activate();
        isDone = true;
    }

}
