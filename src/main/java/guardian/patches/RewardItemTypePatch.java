package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class RewardItemTypePatch {
    @SpireEnum
    public static RewardItem.RewardType GEM;
    @SpireEnum
    public static RewardItem.RewardType GEMALLRARITIES;
}