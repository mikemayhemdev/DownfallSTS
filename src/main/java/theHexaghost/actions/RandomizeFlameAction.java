package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

public class RandomizeFlameAction extends AbstractGameAction {
    public AbstractGhostflame flameToRandomize;
    public int bruh = 0;


    public RandomizeFlameAction() {

    }

    public void update() {

                AbstractGhostflame newgf = GhostflameHelper.activeGhostFlame;
                System.out.println("DEBUG: Old Ghostflame: " + newgf.getName());
                //this is only called by a relic, so it'll use relic rng here
                bruh = AbstractDungeon.relicRng.random(0, 2);

                if (bruh == 0) {
                    newgf = new SearingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                }

                if (bruh == 1) {
                    newgf = new CrushingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                }

                if (bruh == 2) {
                    newgf = new BolsteringGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                }

                System.out.println("DEBUG: Found Ghostflame: " + newgf.getName());

                // randomize until it's different

                if (newgf.getName() == GhostflameHelper.activeGhostFlame.getName()) {
                    System.out.println("DEBUG: Both Ghostflames are identical: " + newgf.getName());
                    while (newgf.getName() == GhostflameHelper.activeGhostFlame.getName()) {
                        bruh = AbstractDungeon.relicRng.random(0, 2);

                        if (bruh == 0) {
                            newgf = new SearingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                        }

                        if (bruh == 1) {
                            newgf = new CrushingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                        }

                        if (bruh == 2) {
                            newgf = new BolsteringGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                        }

                        System.out.println("DEBUG: Found Ghostflame: " + newgf.getName());
                    }
                }

                 System.out.println("DEBUG: Decided on Ghostflame: " + newgf.getName());

                GhostflameHelper.hexaGhostFlames.set((GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame)), newgf);
                newgf.activate();

        isDone = true;
    }

}
