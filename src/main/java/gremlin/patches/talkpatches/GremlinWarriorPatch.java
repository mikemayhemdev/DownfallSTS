package gremlin.patches.talkpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import downfall.downfallMod;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz = GremlinWarrior.class,
        method = "takeTurn"
)
public class GremlinWarriorPatch {
    public static void Prefix(GremlinWarrior __instance) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            ((GremlinCharacter) AbstractDungeon.player).gremlinTalk(__instance);
        }
    }
}