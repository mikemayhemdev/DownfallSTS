package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.powers.OnGainedBlockModifierPower;

@SpirePatch(
        clz= AbstractCreature.class,
        method="addBlock"
)
public class OnGainedBlockPowerPatch {
    @SpireInsertPatch(
            rloc=1,
            localvars={"tmp"}
    )
    public static void Insert(AbstractCreature __instance, int blockAmount, @ByRef float[] tmp) {
        for(AbstractPower p:__instance.powers){
            if(p instanceof OnGainedBlockModifierPower){
                tmp[0] = ((OnGainedBlockModifierPower) p).onGainedBlockModifier(tmp[0]);
            }
        }
    }
}

