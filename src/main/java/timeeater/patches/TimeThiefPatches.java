package timeeater.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import timeeater.relics.SkipNextBossCardReward;
import timeeater.relics.SkipNextBossRelicReward;

public class TimeThiefPatches {

    @SpirePatch(
            clz = BossRelicSelectScreen.class,
            method = "update"
    )
    public static class SkipRelic {
        public static void Prefix(BossRelicSelectScreen __instance) {
            if (AbstractDungeon.player.hasRelic(SkipNextBossRelicReward.ID)) {
                __instance.relics.clear();
                AbstractDungeon.player.loseRelic(SkipNextBossRelicReward.ID);
            }
        }
    }


    @SpirePatch(
            clz = CombatRewardScreen.class,
            method = "setupItemReward"
    )
    public static class SkipCards {
        public static SpireReturn Prefix(CombatRewardScreen __instance) {
            if (AbstractDungeon.player.hasRelic(SkipNextBossCardReward.ID)) {

                AbstractDungeon.player.loseRelic(SkipNextBossCardReward.ID);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
