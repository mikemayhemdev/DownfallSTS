package downfall.patches.vfx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import downfall.util.ThirdSealReward;
import guardian.rewards.GemReward;
import guardian.rewards.GemRewardButRelicRng;
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
                    AbstractRoom temp = AbstractDungeon.getCurrRoom();      // The card reward would roll cards based on current room type
                    if(temp instanceof MonsterRoomBoss){
                        AbstractDungeon.currMapNode.setRoom(new EmptyRoom());   // these lines are used to force it generate card rewards like in hall fights
                        reward.generate_reward_cards();                         // tested to work without affecting the original boss card reward and save & reload friendly
                        AbstractDungeon.currMapNode.setRoom(temp);             //  but might still be buggy
                    }else{
                        reward.generate_reward_cards();
                    }
                } else if ( rewardItem instanceof SealSealReward ) {
                    SealSealReward reward = (SealSealReward) rewardItem;
                    reward.generate_reward_cards();

                } else if ( rewardItem instanceof GemReward ) {
                    GemReward reward = (GemReward) rewardItem;
                    reward.generate_reward_cards();
                }
                else if ( rewardItem instanceof GemRewardButRelicRng) {
                    GemRewardButRelicRng reward = (GemRewardButRelicRng) rewardItem;
                    reward.generate_reward_cards();
                }
                else if ( rewardItem instanceof UpgradedUnknownReward ) {
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

}
