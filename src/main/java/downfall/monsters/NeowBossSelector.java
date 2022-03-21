package downfall.monsters;

import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import downfall.downfallMod;
import downfall.monsters.gauntletbosses.*;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.Collections;


public class NeowBossSelector {

    public static boolean validClass(String key) {

        if (key.equals("downfall:Ironclad") ||
                key.equals("downfall:Silent)") ||
                key.equals("downfall:Defect") ||
                key.equals("downfall:Watcher") ||
                key.equals("downfall:Hermit")) {
            return true;
        }
        return false;
    }

    public static String charbossToGauntlet(String key) {
        switch (key) {
            case "downfall:Ironclad":
                return Ironclad.ID;
            case "downfall:Silent":
                return Silent.ID;
            case "downfall:Defect":
                return Defect.ID;
            case "downfall:Watcher":
                return Watcher.ID;
            case "downfall:Hermit":
                return Hermit.ID;

        }
        return "";
    }

    public static ArrayList<String> returnBossOptions() {

        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> bosses = new ArrayList<>();
        SlimeboundMod.logger.info("Bosses! " + downfallMod.Act1BossFaced + " " + validClass(downfallMod.Act1BossFaced) +
                downfallMod.Act2BossFaced + " " + validClass(downfallMod.Act2BossFaced) +
                downfallMod.Act3BossFaced + " " + validClass(downfallMod.Act3BossFaced));
        if (validClass(downfallMod.Act1BossFaced) && validClass(downfallMod.Act2BossFaced) && validClass(downfallMod.Act3BossFaced)) {


            results.add(charbossToGauntlet(downfallMod.Act1BossFaced));
            results.add(charbossToGauntlet(downfallMod.Act2BossFaced));
            results.add(charbossToGauntlet(downfallMod.Act3BossFaced));

            return results;
        } else {
            bosses.add(Ironclad.ID);
            bosses.add(Silent.ID);
            bosses.add(Defect.ID);
            bosses.add(Watcher.ID);
            bosses.add(Hermit.ID);
            Collections.shuffle(bosses);
            for (int i = 0; i < 3; i++) {
                results.add(bosses.get(i));
            }
            return results;
        }
    }
}
