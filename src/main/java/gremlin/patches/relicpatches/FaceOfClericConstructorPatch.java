package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= FaceOfCleric.class,
        method=SpirePatch.CONSTRUCTOR
)
public class FaceOfClericConstructorPatch {
    public static void Postfix(FaceOfCleric __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            __instance.counter = 0;
        }
    }
}
