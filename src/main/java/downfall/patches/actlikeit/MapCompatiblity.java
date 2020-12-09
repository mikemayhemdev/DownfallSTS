package downfall.patches.actlikeit;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import downfall.patches.EvilModeCharacterSelect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            return SpireReturn.Return(AbstractDungeon.getCurrMapNode().y == 0 ? (AbstractDungeon.id.equals(TheEnding.ID) ? 2 : 14) : 13);
        }
        return SpireReturn.Continue();
    }

    public static int actLikeItCheck()
    {
        try {
            Method method = Class.forName("actlikeit.patches.DungeonMapPatches").getDeclaredMethod("atMapEnd");
            return (int) method.invoke(null);
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
