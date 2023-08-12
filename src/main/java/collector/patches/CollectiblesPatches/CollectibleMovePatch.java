package collector.patches.CollectiblesPatches;

import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

@SpirePatch(
        clz = ShowCardAndObtainEffect.class,
        method = "update"
)
public class CollectibleMovePatch {
    public static void Postfix(ShowCardAndObtainEffect __instance) {
        AbstractCard q = ReflectionHacks.getPrivate(__instance, ShowCardAndObtainEffect.class, "card");
        if (CardModifierManager.hasModifier(q, CollectedCardMod.ID) && __instance.duration < 0.0F) {
            AbstractDungeon.player.masterDeck.removeCard(q);
            CollectorCollection.collection.addToBottom(q);
        }
    }
}