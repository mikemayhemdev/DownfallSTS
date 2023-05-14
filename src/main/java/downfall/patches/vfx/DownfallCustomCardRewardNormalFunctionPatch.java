package downfall.patches.vfx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import downfall.util.ThirdSealReward;
import guardian.rewards.GemReward;
import javassist.CtBehavior;
import sneckomod.util.UpgradedUnknownReward;
import theHexaghost.util.SealSealReward;

public class DownfallCustomCardRewardNormalFunctionPatch {
    // This patch, learned from Packmaster, makes custom card reward save&load friendly. Current CustomReward is using the default constructor
    // for RewardItem, which saves a wrong(an advanced) seed when saving, resulting in the reward cards being different the first time you reload, and
    // affecting normal card rewards too, this patch corrects that.
    @SpirePatch(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class GenerateCardsFromRewardPoolPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void generate_cards(CombatRewardScreen __instance) {
            for (int i = 0; i < __instance.rewards.size(); i++) {
                RewardItem rewardItem = __instance.rewards.get(i);

                if ( rewardItem instanceof ThirdSealReward ) {
                    ThirdSealReward reward = (ThirdSealReward) rewardItem;
                    reward.generate_reward_cards();

                } else if ( rewardItem instanceof SealSealReward ) {
                    SealSealReward reward = (SealSealReward) rewardItem;
                    reward.generate_reward_cards();

                } else if ( rewardItem instanceof GemReward ) {
                    GemReward reward = (GemReward) rewardItem;
                    reward.generate_reward_cards();
                } else if ( rewardItem instanceof UpgradedUnknownReward ) {
                    UpgradedUnknownReward reward = (UpgradedUnknownReward) rewardItem;
                    reward.generate_reward_cards();
                }
            }
            AbstractDungeon.combatRewardScreen.positionRewards();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "show");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
//    public static SpireReturn<?> plz(RewardItem __instance) {
//        if (__instance instanceof ThirdSealReward || __instance instanceof GemReward || __instance instanceof SealSealReward) {
//            return SpireReturn.Return();
//        }
//        return SpireReturn.Continue();
//    }
//
//    public static class Locator extends SpireInsertLocator {
//
//        @Override
//        public int[] Locate(CtBehavior ctBehavior) throws Exception {
//            Matcher m = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getRewardCards");
//            return LineFinder.findInOrder(ctBehavior, m);
//        }
//    }
}

