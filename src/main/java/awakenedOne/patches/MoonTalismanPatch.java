package awakenedOne.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "<class>")
public class MoonTalismanPatch {
    public static SpireField<Boolean> inBottleTalisman = new SpireField(() -> Boolean.FALSE);

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            MoonTalismanPatch.inBottleTalisman.set(__result, MoonTalismanPatch.inBottleTalisman.get(__instance));
            return __result;
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.cards.AbstractCard", "atTurnStart");
            return LineFinder.findAllInOrder(ctBehavior, (Matcher)methodCallMatcher);
        }
    }
}
