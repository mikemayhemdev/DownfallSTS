package downfall.monsters;

import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import downfall.monsters.gauntletbosses.*;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.Collections;


public class NeowBossSelector {

    public static boolean validClass(String key) {
        switch (key) {
            //This doesn't make sense but this works and checking against the strings with an OR logic check doesn't
            case "downfall:Ironclad":
                return true;
            case "downfall:Silent":
                return true;
            case "downfall:Defect":
                return true;
            case "downfall:Watcher":
                return true;
            case "downfall:Hermit":
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
        /*
        SlimeboundMod.logger.info("Bosses! " + downfallMod.Act1BossFaced + " " + validClass(downfallMod.Act1BossFaced) +
                downfallMod.Act2BossFaced + " " + validClass(downfallMod.Act2BossFaced) +
                downfallMod.Act3BossFaced + " " + validClass(downfallMod.Act3BossFaced));
          */
        if (validClass(downfallMod.Act1BossFaced) && validClass(downfallMod.Act2BossFaced) && validClass(downfallMod.Act3BossFaced)) {



            results.add(charbossToGauntlet(downfallMod.Act1BossFaced));
            results.add(charbossToGauntlet(downfallMod.Act2BossFaced));
            results.add(charbossToGauntlet(downfallMod.Act3BossFaced));

            if (results.get(2) == Ironclad.ID){
                Collections.swap(results, 2,0);
            }
            else if (results.get(1) == Ironclad.ID){
                Collections.swap(results, 1,0);
            }

            return results;
        } else {
            bosses.add(Ironclad.ID);
            bosses.add(Silent.ID);
            bosses.add(Defect.ID);
            bosses.add(Watcher.ID);
            bosses.add(Hermit.ID);
            Collections.shuffle(bosses, AbstractDungeon.cardRandomRng.random);
            for (int i = 0; i < 3; i++) {
                results.add(bosses.get(i));
            }

            if (results.get(2) == Ironclad.ID){
                Collections.swap(results, 2,0);
            }
            else if (results.get(1) == Ironclad.ID){
                Collections.swap(results, 1,0);
            }
            return results;
        }
    }
}
