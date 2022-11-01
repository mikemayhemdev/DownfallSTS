package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import downfall.downfallMod;

@SpirePatch(
        clz = FaceOfCleric.class,
        method = SpirePatch.CONSTRUCTOR
)
public class FaceOfClericConstructorPatch {
    public static void Postfix(FaceOfCleric __instance) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            __instance.counter = 0;
        }
    }
}
