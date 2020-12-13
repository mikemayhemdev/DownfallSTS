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

        //There's a high possibility that this is the second y check, which the actlikeit patch isn't normally intended to change.
        //Due to this, an extra check is placed here.

        //If this is the first y check, this won't cause any problems since it would fail anyways if the act is TheEnding.
        if (AbstractDungeon.id.equals(TheEnding.ID))
        {
            return SpireReturn.Return(AbstractDungeon.getCurrMapNode().y);

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
