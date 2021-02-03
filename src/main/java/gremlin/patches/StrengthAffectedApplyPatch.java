package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.cards.StrengthAffectedCard;

@SpirePatch(
        clz= AbstractCard.class,
        method="applyPowers"
)
public class StrengthAffectedApplyPatch {
    @SpireInsertPatch(
            rloc=19,
            localvars={"p", "tmp"}
    )
    public static void Insert(AbstractCard __instance, AbstractPower p, @ByRef float[] tmp) {
        if(__instance instanceof StrengthAffectedCard && p instanceof StrengthPower){
            for(int i=1; i < ((StrengthAffectedCard) __instance).strengthMultiplier(); i++){
                tmp[0] = p.atDamageGive(tmp[0], __instance.damageTypeForTurn);
            }
        }
    }
}

