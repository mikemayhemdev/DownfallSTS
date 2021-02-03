package gremlin.patches.talkpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinTsundere;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= GremlinTsundere.class,
        method="takeTurn"
)
public class GremlinTsunderePatch {
    public static void Prefix(GremlinTsundere __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter) AbstractDungeon.player).gremlinTalk(__instance);
        }
    }
}