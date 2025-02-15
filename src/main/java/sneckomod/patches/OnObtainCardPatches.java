package sneckomod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;

public class OnObtainCardPatches {
    @SpirePatch(clz = ShowCardAndObtainEffect.class, method = "update")
    public static class OnPickupCardDoStuffPatch {
        public static void Postfix(ShowCardAndObtainEffect __instance) {
            if (!SneckoMod.incomingPicks.isEmpty() && __instance.isDone) {
                SneckoMod.nextGift();
            }
        }
    }

    @SpirePatch(clz = FastCardObtainEffect.class, method = "update")
    public static class OnPickupCardDoStuffPatch2 {
        public static void Postfix(FastCardObtainEffect __instance) {
            if (!SneckoMod.incomingPicks.isEmpty() && __instance.isDone) {
                SneckoMod.nextGift();
            }
        }
    }
}