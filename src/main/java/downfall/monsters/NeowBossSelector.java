package downfall.monsters;

import downfall.monsters.gauntletbosses.*;

import java.util.ArrayList;
import java.util.Collections;

public class NeowBossSelector {
    public static ArrayList<String> returnBossOptions() {
        ArrayList<String> bosses = new ArrayList<>();
        bosses.add(Ironclad.ID);
        bosses.add(Silent.ID);
        bosses.add(Defect.ID);
        bosses.add(Watcher.ID);
        bosses.add(Hermit.ID);
        Collections.shuffle(bosses);
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            results.add(bosses.get(i));
        }
        return results;
    }
}
