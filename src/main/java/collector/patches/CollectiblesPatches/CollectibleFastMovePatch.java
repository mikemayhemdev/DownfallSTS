package collector.patches.CollectiblesPatches;

import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import collector.cards.collectibles.AbstractCollectibleCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

@SpirePatch(
        clz = FastCardObtainEffect.class,
        method = "update"
)
public class CollectibleFastMovePatch {
    public static void Postfix(FastCardObtainEffect __instance) {
        AbstractCard q = ReflectionHacks.getPrivate(__instance, FastCardObtainEffect.class, "card");
        if (CardModifierManager.hasModifier(q, CollectedCardMod.ID) && __instance.duration < 0.0F) {
            AbstractDungeon.player.masterDeck.removeCard(q);
            CollectorCollection.collection.addToBottom(q);
        }
    }
}
