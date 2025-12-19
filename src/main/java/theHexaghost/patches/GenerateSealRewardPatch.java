package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.relics.TheBrokenSeal;
import theHexaghost.util.SealSealReward;

public class GenerateSealRewardPatch {
@SpirePatch(
        clz = AbstractRoom.class,
        method = "addPotionToRewards",
        paramtypez = {}
)
public static class DropSeals {
    public static void Postfix(AbstractRoom __instance) {
        if(AbstractDungeon.player instanceof TheHexaghost && (!AbstractDungeon.player.hasRelic(TheBrokenSeal.ID) && HexaMod.isHexaghostSealsEnabled()) ) {

            if (__instance instanceof MonsterRoomElite) {
                __instance.rewards.add(new SealSealReward());
            }

        }
    }
}

}
