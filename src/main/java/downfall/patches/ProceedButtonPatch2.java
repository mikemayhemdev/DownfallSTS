
package downfall.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import downfall.downfallMod;
import slimebound.SlimeboundMod;

@SpirePatch(
        clz = ProceedButton.class,
        method = "goToDoubleBoss"
)
public class ProceedButtonPatch2 {

    public static SpireReturn Prefix(ProceedButton __instance) {
        if (EvilModeCharacterSelect.evilMode) {
            SlimeboundMod.logger.info("HIT THE DOUBLE BOSS PATCH!");
            downfallMod.resetBossList();
            downfallMod.possEncounterList.remove(downfallMod.Act1BossFaced);
            downfallMod.possEncounterList.remove(downfallMod.Act2BossFaced);
            downfallMod.possEncounterList.remove(downfallMod.Act3BossFaced);

            AbstractDungeon.bossKey = downfallMod.possEncounterList.get(0);
            CardCrawlGame.music.fadeOutBGM();
            CardCrawlGame.music.fadeOutTempBGM();
            MapRoomNode node = new MapRoomNode(-1, 15);
            node.room = new MonsterRoomBoss();
            AbstractDungeon.nextRoom = node;
            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.nextRoomTransitionStart();
            __instance.hide();
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

}