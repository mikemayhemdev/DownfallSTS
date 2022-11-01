package downfall.patches;

import chronoMods.TogetherManager;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import downfall.events.HeartEvent;
import javassist.CtBehavior;
import slimebound.SlimeboundMod;


@SpirePatch(
        clz = NeowRoom.class,
        method = SpirePatch.CONSTRUCTOR
)
public class NeowEventPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void ChangeEvent(NeowRoom __instance, boolean isDone) {
        boolean switchToHeart = true;
        if (EvilModeCharacterSelect.evilMode) {
            SlimeboundMod.logger.info("Neow Event detected evil mode");
            if (Loader.isModLoaded("chronoMods")) {
                SlimeboundMod.logger.info("Neow Event detected Spire With Friends");
                if (TogetherManager.gameMode.equals(TogetherManager.mode.Coop)) {
                    SlimeboundMod.logger.info("Neow Event detected Co-op Mode");
                    switchToHeart = false;
                }
            }
            if (switchToHeart) __instance.event = new HeartEvent(isDone);
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