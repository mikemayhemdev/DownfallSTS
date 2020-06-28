package downfall.patches.actlikeit;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.patches.EvilModeCharacterSelect;

@SpirePatch(
        optional = true,
        cls = "actlikeit.patches.DungeonMapPatches",
        method = "atMapEnd"
)
public class MapCompatiblity {
    @SpirePrefixPatch
    public static SpireReturn<Integer> evilMode()
    {
        if (EvilModeCharacterSelect.evilMode)
        {
            return SpireReturn.Return(AbstractDungeon.getCurrMapNode().y == 0 ? 14 : 0);
        }
        return SpireReturn.Continue();
    }

    public static int actLikeItCheck()
    {
        return actlikeit.patches.DungeonMapPatches.atMapEnd();
    }
}
