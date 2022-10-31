package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import gremlin.characters.GremlinCharacter;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.lastCombatMetricKey;

@SpirePatch(clz = MonsterRoomElite.class, method = "dropReward")
public class MuggedByElitePatch
{
    private static final String TEXT = CardCrawlGame.languagePack.getUIString("Gremlin:BonusGold").TEXT[0];

    @SpireInsertPatch(
            rloc=10
    )
    public static void Insert(MonsterRoomElite __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter && __instance.mugged) {
            __instance.rewards.removeIf(reward -> reward.type == RewardItem.RewardType.GOLD);
        } else if (AbstractDungeon.player instanceof GremlinCharacter) {
            if (lastCombatMetricKey.equals(MonsterHelper.GREMLIN_NOB_ENC) || lastCombatMetricKey.equals(MonsterHelper.GREMLIN_LEADER_ENC)) {
                RewardItem bonusGold = new RewardItem(20, true);
                bonusGold.text = bonusGold.goldAmt + TEXT;
                __instance.rewards.add(bonusGold);
            }
        }
    }
}

