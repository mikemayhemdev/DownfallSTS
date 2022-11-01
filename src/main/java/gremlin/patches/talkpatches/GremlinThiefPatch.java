package gremlin.patches.talkpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinThief;
import downfall.downfallMod;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz = GremlinThief.class,
        method = "takeTurn"
)
public class GremlinThiefPatch {
    public static void Prefix(GremlinThief __instance) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            ((GremlinCharacter) AbstractDungeon.player).gremlinTalk(__instance);
        }
    }
}