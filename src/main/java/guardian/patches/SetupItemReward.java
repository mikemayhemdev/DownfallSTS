package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import guardian.powers.GemFinderPower;
import javassist.CtBehavior;

@SpirePatch(clz= CombatRewardScreen.class, method="setupItemReward")
public class SetupItemReward {

    @SpirePrefixPatch
    public static void Prefix(CombatRewardScreen rewardScreen) {
        if(AbstractDungeon.player.hasPower(GemFinderPower.POWER_ID) && AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            ((GemFinderPower)AbstractDungeon.player.getPower(GemFinderPower.POWER_ID)).onTrigger();
        }
    }

}