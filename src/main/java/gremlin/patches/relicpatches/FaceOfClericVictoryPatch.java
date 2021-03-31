package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= FaceOfCleric.class,
        method="onVictory"
)
public class FaceOfClericVictoryPatch {
    public static SpireReturn<Void> Prefix(FaceOfCleric __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            __instance.counter += 1;
            if (__instance.counter == 5) {
                __instance.counter = 0;
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
