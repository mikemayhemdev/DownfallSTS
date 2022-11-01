package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = EnableEndTurnButtonAction.class, method = "update")
public class CowerPatch {

    @SpirePrefixPatch()
    public static SpireReturn patch(EnableEndTurnButtonAction __instance) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            if (((GremlinCharacter) AbstractDungeon.player).isCowering()) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}
