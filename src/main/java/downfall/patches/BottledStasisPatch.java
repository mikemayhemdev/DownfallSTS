package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class BottledStasisPatch {
    public static SpireField<Boolean> inBottledStasis = new SpireField<>(() -> false);
    public static SpireField<Boolean> inStasisEgg = new SpireField<>(() -> false);
    public static SpireField<Boolean> inBottledAnomaly = new SpireField<>(() -> false);
    public static SpireField<Boolean> inBottledCode = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            inBottledStasis.set(__result, inBottledStasis.get(__instance));
            inStasisEgg.set(__result, inStasisEgg.get(__instance));
            inBottledAnomaly.set(__result, inBottledAnomaly.get(__instance));
            inBottledCode.set(__result, inBottledCode.get(__instance));
            /*
            //TODO - Tricky one.  Can we just split this into multiple patches instead of trying to do an all in one?
            if (__instance instanceof AbstractGuardianCard) {

                ((AbstractGuardianCard) __result).socketCount = ((AbstractGuardianCard) __instance).socketCount;
                ((AbstractGuardianCard) __result).sockets = new ArrayList<>(((AbstractGuardianCard) __instance).sockets);
                ((AbstractGuardianCard) __result).updateDescription();
            }

             */


            return __result;
        }
    }
}