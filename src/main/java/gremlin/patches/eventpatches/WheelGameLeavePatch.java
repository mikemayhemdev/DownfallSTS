package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = GremlinWheelGame.class, method = "buttonEffect")
public class WheelGameLeavePatch
{
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:WheelGame");
    private static final String LOOT_TEXT = strings.OPTIONS[1];

    @SpireInsertPatch(
            rloc=18
    )
    public static SpireReturn<Void> FirstInsert(GremlinWheelGame __instance, int buttonPressed) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if(WheelGameResultPatch.hasReward){
                __instance.imageEventText.clearAllDialogs();
                __instance.imageEventText.setDialogOption(LOOT_TEXT);
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=20
    )
    public static SpireReturn<Void> Insert(GremlinWheelGame __instance, int buttonPressed) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if(WheelGameResultPatch.hasReward){
                WheelGameResultPatch.hasReward = false;
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractDungeon.getCurrRoom().addRelicToRewards(r);
                AbstractDungeon.getCurrRoom().addGoldToRewards(300);
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.combatRewardScreen.open();
                __instance.imageEventText.clearAllDialogs();
                __instance.imageEventText.setDialogOption(GremlinWheelGame.OPTIONS[8]);
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}
