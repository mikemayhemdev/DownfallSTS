package collector.patches;

import collector.CollectorCollection;
import collector.actions.DrawCardFromCollectionAction;
import collector.powers.AlwaysMorePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;

public class DrawFromCollection {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnPostDrawRelics"
    )
    public static class AbstractPlayerApplyStartOfTurnPostDrawRelicsPatch {
        public static void Prefix(AbstractPlayer __instance) {
            if (!CollectorCollection.collection.isEmpty()) {
                atb(new DrawCardFromCollectionAction());
                if (AbstractDungeon.player.hasPower(AlwaysMorePower.POWER_ID)) {
                    for (int i = 0; i < AbstractDungeon.player.getPower(AlwaysMorePower.POWER_ID).amount; i++) {
                        atb(new DrawCardFromCollectionAction());
                    }
                }
            }
        }
    }
}
