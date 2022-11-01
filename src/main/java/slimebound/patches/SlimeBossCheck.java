package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import slimebound.SlimeboundMod;

@SpirePatch(clz = SlimeBoss.class, method = "usePreBattleAction")
public class SlimeBossCheck {

    public static void Prefix(SlimeBoss sb) {

        SlimeboundMod.foughtSlimeBoss = true;
        //  //SlimeboundMod.logger.info("Slimebound Character has fought the Slime Boss.");

    }
}
