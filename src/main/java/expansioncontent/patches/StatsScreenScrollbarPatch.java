package expansioncontent.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import expansioncontent.expansionContentMod;

@SpirePatch2(
        clz = StatsScreen.class,
        method = "calculateScrollBounds"
)
public class StatsScreenScrollbarPatch {
    @SpirePostfixPatch
    public static void Postfix(StatsScreen __instance) {
        float currentUpperBound = (Float) ReflectionHacks.getPrivate(__instance, StatsScreen.class, "scrollUpperBound");
        float downfallAchievementsHeight = expansionContentMod.downfallAchievementGrid.calculateHeight();
        ReflectionHacks.setPrivate(__instance, StatsScreen.class, "scrollUpperBound", currentUpperBound + (downfallAchievementsHeight + 50F));
    }
}