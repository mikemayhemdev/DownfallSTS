package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
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
        if( AbstractDungeon.player instanceof TheHexaghost && (!AbstractDungeon.player.hasRelic(TheBrokenSeal.ID)) ) {

//            int chance = 15;
//            if (__instance instanceof MonsterRoomElite) {
//                chance = 35;
//            } else if (__instance instanceof MonsterRoomBoss) {
//                chance = 55;
//            }
//
//            chance = chance + (int)(HexaMod.bonus_seal_drop_chance * 0.8);
//
//            if (__instance.rewards.size() >= 5) {
//                chance = 0;
//            }
////            System.out.println("Seal Dropchance: " + chance);
//            if (AbstractDungeon.potionRng.random(0, 99) <= chance) {
//                __instance.rewards.add(new SealSealReward());
//            }

            if (__instance instanceof MonsterRoomElite || __instance instanceof MonsterRoomBoss) {
                __instance.rewards.add(new SealSealReward());
            }

        }
    }
}

}
