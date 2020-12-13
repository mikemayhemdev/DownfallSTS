package champ.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import guardian.cards.AbstractGuardianCard;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class SignatureMovePatch {
    public static SpireField<Boolean> inSignatureMove = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            inSignatureMove.set(__result, inSignatureMove.get(__instance));

            return __result;
        }
    }
}