
package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import downfall.monsters.FleeingMerchant;
import downfall.rooms.HeartShopRoom;

@SpirePatch(
        clz = CombatRewardScreen.class,
        method = "setupItemReward"
)
public class CombatRewardScreenRemoveCardPatch {
    public static void Postfix(CombatRewardScreen __instance) {
        if (AbstractDungeon.getCurrRoom() instanceof HeartShopRoom && (FleeingMerchant.helpEscaped || FleeingMerchant.DEAD)) {
            __instance.rewards.clear();
            FleeingMerchant.helpEscaped = false;
            FleeingMerchant.helpDied = false;
        }
    }
}