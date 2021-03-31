package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.powers.ConditionalModifyBlockPower;

@SpirePatch(
        clz= AbstractCard.class,
        method="applyPowersToBlock"
)
public class ConditionalModifyBlockApplyPatch {
    @SpireInsertPatch(
            rloc=4,
            localvars={"p", "tmp"}
    )
    public static void Insert(AbstractCard __instance, AbstractPower p, @ByRef float[] tmp) {
        if(p instanceof ConditionalModifyBlockPower){
            tmp[0] = ((ConditionalModifyBlockPower) p).conditionalModifyBlock(tmp[0], __instance);
        }
    }
}

