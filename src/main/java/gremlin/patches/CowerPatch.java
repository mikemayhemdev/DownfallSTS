package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = EnableEndTurnButtonAction.class, method = "update")
public class CowerPatch
{

    @SpirePrefixPatch()
    public static SpireReturn patch(EnableEndTurnButtonAction __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if (((GremlinCharacter) AbstractDungeon.player).isCowering()) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}
