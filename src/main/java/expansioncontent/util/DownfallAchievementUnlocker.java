package expansioncontent.util;

import com.megacrit.cardcrawl.core.Settings;
import downfall.downfallMod;
import expansioncontent.achievements.DownfallAchievementGrid;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class DownfallAchievementUnlocker {
    private static DownfallAchievementGrid achievementGrid;

    public static void setAchievementGrid(DownfallAchievementGrid grid) {
        achievementGrid = grid;
    }

    public static void unlockAchievement(String key) {
        String fullKey = downfallMod.makeID(key);
        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            if (!achievementPref.getBoolean(fullKey, false)) {
                achievementPref.putBoolean(fullKey, true);
            }

            achievementPref.flush();

            if (achievementGrid != null) {
                achievementGrid.updateAchievementStatus();
                if (!key.equals("EVIL_ONE") && achievementGrid.areAllAchievementsUnlockedExceptEvilOne()) {
                    unlockAchievement("EVIL_ONE");
                }
            }
        }
    }
}