package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = MonsterRoomElite.class, method = "dropReward")
public class MuggedByElitePatch
{
    @SpireInsertPatch(
            rloc=10
    )
    public static void Insert(MonsterRoomElite __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter && __instance.mugged) {
            __instance.rewards.removeIf(reward -> reward.type == RewardItem.RewardType.RELIC || reward.type == RewardItem.RewardType.GOLD);
        }
    }
}

