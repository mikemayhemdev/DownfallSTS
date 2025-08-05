package sneckomod.patches;

import awakenedOne.patches.MoonTalismanPatch;
import awakenedOne.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class BottledD8Patch {
    public static SpireField<Boolean> inD8 = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {

            if (Wiz.isInCombat()) {
                inD8.set(__result, inD8.get(__instance));
            }

            if (!Wiz.isInCombat()) {
                inD8.set(__result, Boolean.FALSE);
            }

            return __result;
        }
    }
}