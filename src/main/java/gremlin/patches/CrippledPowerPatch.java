package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.powers.WeakPower;
import gremlin.powers.CrippledPower;

@SpirePatch(
        clz= WeakPower.class,
        method="atEndOfRound"
)
public class CrippledPowerPatch {
    public static SpireReturn Prefix(WeakPower __instance){
        if(__instance.owner.hasPower(CrippledPower.POWER_ID)){
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
