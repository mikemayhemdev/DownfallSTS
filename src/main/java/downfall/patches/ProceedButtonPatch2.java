package downfall.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

@SpirePatch(
        clz = ProceedButton.class,
        method = "goToDoubleBoss"
)
public class ProceedButtonPatch2 {

    public static SpireReturn Prefix(ProceedButton __instance) {
        if (EvilModeCharacterSelect.evilMode) {
            CardCrawlGame.music.fadeOutBGM();// 252
            CardCrawlGame.music.fadeOutTempBGM();// 253
            MapRoomNode node = new MapRoomNode(-1, 15);// 254
            node.room = new VictoryRoom(VictoryRoom.EventType.HEART);// 255
            AbstractDungeon.nextRoom = node;// 256
            AbstractDungeon.closeCurrentScreen();// 257
            AbstractDungeon.nextRoomTransitionStart();// 258
            __instance.hide();// 259
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

}