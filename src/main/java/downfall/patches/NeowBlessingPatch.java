package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.trials.CustomTrial;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import downfall.dailymods.ExchangeController;
import downfall.relics.NeowBlessing;

import java.util.ArrayList;


public class NeowBlessingPatch {
    @SpirePatch(
            clz = CustomModeScreen.class,
            method = "addNonDailyMods"
    )
    public static class CustomModeScreenPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CustomModeScreen _instance, CustomTrial trial, ArrayList<String> modIds) {
            for (String modId : modIds) {
                if (modId.equals(ExchangeController.ID)) {
                    trial.setShouldKeepStarterRelic(false);
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatches(
            {
                    @SpirePatch(
                            clz = TopPanel.class,
                            method = "renderPotions"
                    ),
                    @SpirePatch(
                            clz = TopPanel.class,
                            method = "updatePotions"
                    )
            }
    )
    public static class PotionPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TopPanel _instance) {
            if (AbstractDungeon.player.hasRelic(NeowBlessing.ID)) {
                for (AbstractPotion p : AbstractDungeon.player.potions) {
                    if (!(p instanceof PotionSlot)) {
                        AbstractDungeon.topPanel.destroyPotion(p.slot);
                    }
                }
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = ObtainPotionAction.class,
            method = "update"
    )
    public static class ObtainPotionPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(ObtainPotionAction _instance) {
            if (AbstractDungeon.player.hasRelic(NeowBlessing.ID)) {
                AbstractDungeon.player.getRelic(NeowBlessing.ID).flash();
                _instance.isDone = true;
            }
            return SpireReturn.Continue();
        }
    }

}