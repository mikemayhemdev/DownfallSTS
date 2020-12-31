package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.dailymods.StatusAbuse;

public class AllRelicsAreCircletsPatch {

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "spawnRelicAndObtain",
            paramtypez = {float.class, float.class, AbstractRelic.class}
    )
    public static class RelicPatch {

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractRoom __instance, float x, float y, AbstractRelic relic) {
            if ((CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(StatusAbuse.ID)) || ModHelper.isModEnabled(StatusAbuse.ID)) {
                if (AbstractDungeon.player.hasRelic("Circlet")) {
                    AbstractRelic circ = AbstractDungeon.player.getRelic("Circlet");
                    ++circ.counter;
                    circ.flash();
                } else {
                    AbstractRelic relic2 = new Circlet();
                    relic2.spawn(x, y);
                    relic2.obtain();
                    relic2.isObtained = true;
                    relic2.isAnimating = false;
                    relic2.isDone = false;
                    relic2.flash();
                }
                return SpireReturn.Return(null);
            } else {
               return SpireReturn.Continue();
            }
        }
    }


}
