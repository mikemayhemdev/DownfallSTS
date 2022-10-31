package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.neow.NeowEvent;
import downfall.downfallMod;
import javassist.CtBehavior;

@SpirePatch(
        clz = NeowEvent.class,
        method = "update"
)
public class SneckoStandardPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void SetTheThing(NeowEvent __instance) {
        downfallMod.readyToDoThing = true;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(NeowEvent.class, "setPhaseToEvent");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
        }
    }
}