package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ReplaceHeartKillBonusPatch {

    @SpirePatch(
            clz = VictoryScreen.class,
            method = "createGameOverStats"
    )
    public static class VictoryScreenPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(VictoryScreen __instance) {
            if (EvilModeCharacterSelect.evilMode) {

            }
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");

                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[5]};
            }
        }
    }

    @SpirePatch(
            clz = DeathScreen.class,
            method = "createGameOverStats"
    )
    public static class DeathScreenPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(DeathScreen __instance) {
            if (EvilModeCharacterSelect.evilMode) {

            }
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");

                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[5]};
            }
        }
    }
}