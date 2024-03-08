package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import theHexaghost.HexaMod;
import theHexaghost.util.SealSealReward;

public class GenerateSealRewardPatch {
@SpirePatch(
        clz = AbstractRoom.class,
        method = "addPotionToRewards",
        paramtypez = {}
)
public static class DropSeals {
    public static void Postfix(AbstractRoom __instance) {
        System.out.println("This is actually working");
//        AbstractRoom current_room = AbstractDungeon.getCurrRoom();
        int chance = 15;
        if (__instance instanceof MonsterRoomElite) {
            chance = 35;
        } else if (__instance instanceof MonsterRoomBoss) {
            chance = 55;
        }

        chance = chance + HexaMod.bonus_seal_drop_chance;

        if (__instance.rewards.size() >= 5) {
            chance = 0;
        }
        System.out.println("Seal Dropchance: " + chance);
        if (AbstractDungeon.potionRng.random(0, 99) <= chance) {
            __instance.rewards.add(new SealSealReward());
        }
    }
}

}
