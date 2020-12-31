package collector.patches;

import basemod.ReflectionHacks;
import collector.CollectorCollection;
import collector.cards.Collectibles.AbstractCollectibleCard;
import collector.cards.Collectibles.SentryCore;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

@SpirePatch(
        clz = ShowCardAndObtainEffect.class,
        method = "update"
)
public class  CollectibleMovePatch {
    public static void Postfix(ShowCardAndObtainEffect __instance) {
        AbstractCard q = (AbstractCard) ReflectionHacks.getPrivate(__instance, ShowCardAndObtainEffect.class, "card");
        if (q instanceof AbstractCollectibleCard && __instance.duration < 0.0F) {
            AbstractDungeon.player.masterDeck.removeCard(q);
            CollectorCollection.collection.addToBottom(q);
            if (q instanceof SentryCore && AbstractDungeon.player.maxOrbs < 3){
                AbstractDungeon.player.maxOrbs += 3;
            }
        }
    }
}