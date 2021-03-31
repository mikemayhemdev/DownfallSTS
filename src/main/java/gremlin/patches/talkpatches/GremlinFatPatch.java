package gremlin.patches.talkpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= GremlinFat.class,
        method="takeTurn"
)
public class GremlinFatPatch {
    public static void Prefix(GremlinFat __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter) AbstractDungeon.player).gremlinTalk(__instance);
        }
    }
}