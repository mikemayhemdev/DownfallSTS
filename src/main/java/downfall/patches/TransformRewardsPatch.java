package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import downfall.dailymods.TransformRewards;
import downfall.util.TransformCardReward;

import java.util.ArrayList;

@SpirePatch(
    clz=CombatRewardScreen.class,
    method="setupItemReward"
)
public class TransformRewardsPatch {
    public static void Postfix(CombatRewardScreen __instance) {
        if ((CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(TransformRewards.ID)) || ModHelper.isModEnabled(TransformRewards.ID)) {
            ArrayList<RewardItem> rewardsToRemove = new ArrayList<RewardItem>();
            ArrayList<RewardItem> rewardsToAdd = new ArrayList<RewardItem>();
            for (RewardItem reward : __instance.rewards) {
                if (reward.type == RewardItem.RewardType.CARD) {
                    rewardsToRemove.add(reward);
                    rewardsToAdd.add(new TransformCardReward());
                }
            }
            __instance.rewards.removeAll(rewardsToRemove);
            __instance.rewards.addAll(rewardsToAdd);
            __instance.positionRewards();
        }
    }
}
