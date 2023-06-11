package collector.patches;

import collector.CollectorCollection;
import collector.actions.DrawCardFromCollectionAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static collector.util.Wiz.atb;

public class DrawFromCollection {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnPostDrawRelics"
    )
    public static class AbstractPlayerApplyStartOfTurnPostDrawRelicsPatch {
        public static void Prefix(AbstractPlayer __instance) {
            if (!CollectorCollection.collection.isEmpty())
                atb(new DrawCardFromCollectionAction());
        }
    }
}
