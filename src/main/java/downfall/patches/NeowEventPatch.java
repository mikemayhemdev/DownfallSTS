package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import downfall.events.HeartEvent;
import javassist.CtBehavior;
import sneckomod.SneckoMod;

@SpirePatch(
        clz = NeowRoom.class,
        method = SpirePatch.CONSTRUCTOR
)
public class NeowEventPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void ChangeEvent(NeowRoom __instance, boolean isDone) {
        if (EvilModeCharacterSelect.evilMode) {
            __instance.event = new HeartEvent(isDone);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractEvent.class, "onEnterRoom");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}