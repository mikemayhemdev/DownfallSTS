package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import downfall.powers.FairyPotionPower;

public class MonstersAreDeadPatch {
    @SpirePatch(
            clz = MonsterGroup.class,
            method = "areMonstersBasicallyDead"
    )

    public static class CurrMapNodeSteal {
        public static boolean Postfix(boolean __result, MonsterGroup __instance) {
            if (FairyPotionPower.CANNOT_END)
                return false;
            return __result;
        }
    }
}
