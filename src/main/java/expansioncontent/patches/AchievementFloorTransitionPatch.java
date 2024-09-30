package expansioncontent.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.util.DownfallAchievementVariables;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "nextRoomTransitionStart"
)
public class AchievementFloorTransitionPatch {
    public static void Postfix() {
        DownfallAchievementVariables.resetFloorAchievementVariables();
    }
}