package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.expansionContentMod;

@SpirePatch(
        clz = AbstractCard.class,
        method = "hasEnoughEnergy"
)
public class CantDoublePlayBossCardsPatch {
    public static boolean playedBossCardThisTurn = false;

    public static boolean Postfix(boolean __result, AbstractCard __instance) {
        if (!__instance.freeToPlayOnce && __instance.hasTag(expansionContentMod.STUDY) && playedBossCardThisTurn) {
            __instance.cantUseMessage = "I have already played a Boss card this turn...";
            return false;
        }
        return __result;
    }
}