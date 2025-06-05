package awakenedOne.patches;

import awakenedOne.cardmods.ConjureMod;
import awakenedOne.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import guardian.cards.AbstractGuardianCard;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "<class>")
public class MoonTalismanPatch {
    public static SpireField<Boolean> inBottleTalisman = new SpireField(() -> Boolean.FALSE);

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {

            if (Wiz.isInCombat()) {
                //soularoid is not real, it does not exist, it cannot hurt you
                MoonTalismanPatch.inBottleTalisman.set(__result, MoonTalismanPatch.inBottleTalisman.get(__instance));
            }

            if (!Wiz.isInCombat()) {
                //todo: decide if this is needed
//                if ((CardModifierManager.hasModifier(__result, ConjureMod.ID))) {
//                    CardModifierManager.removeModifiersById(__result, ConjureMod.ID, true);
//                }
                MoonTalismanPatch.inBottleTalisman.set(__result, Boolean.FALSE);
            }

            if (__instance instanceof AbstractGuardianCard) {

                ((AbstractGuardianCard) __result).socketCount = ((AbstractGuardianCard) __instance).socketCount;

                ((AbstractGuardianCard) __result).sockets = new ArrayList<>(((AbstractGuardianCard) __instance).sockets);

                ((AbstractGuardianCard) __result).updateDescription();
            }

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
