package expansioncontent.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import expansioncontent.expansionContentMod;

@SpirePatch(clz = StatsScreen.class, method = "update")
public class UpdateAchievements {
    public static void Postfix(StatsScreen __instance) {
        expansionContentMod.downfallAchievementGrid.update();
    }
}