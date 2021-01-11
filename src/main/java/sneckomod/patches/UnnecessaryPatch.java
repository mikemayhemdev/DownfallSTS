package sneckomod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import sneckomod.cards.ImprovisedAttack;
import sneckomod.cards.ImprovisedGuard;

public class UnnecessaryPatch {

    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToDrawPileEffect.class, method = "update")
    public static class InDraw {
        public static void Prefix(AbstractGameEffect __instance) {
            if (__instance.duration == (float) ReflectionHacks.getPrivateStatic(__instance.getClass(), "EFFECT_DUR")) {
                ImprovisedAttack.storage = ReflectionHacks.getPrivate(__instance, __instance.getClass(), "card");
                ImprovisedGuard.storage = ReflectionHacks.getPrivate(__instance, __instance.getClass(), "card");
            }
        }
    }
}