
package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import downfall.monsters.NeowBossFinal;

@SpirePatch(
        clz = ProceedButton.class,
        method = "goToTrueVictoryRoom"
)
public class EndingDoubleFightPatch {
    public static SpireReturn<Void> Prefix(ProceedButton __instance) {
        AbstractRoom curRoom = AbstractDungeon.getCurrRoom();
        if (curRoom instanceof MonsterRoomBoss) {
            if (EvilModeCharacterSelect.evilMode && AbstractDungeon.id.equals("TheEnding")) {
                System.out.println(AbstractDungeon.getCurrMapNode().x);
                if (!AbstractDungeon.bossKey.equals(NeowBossFinal.ID)) {
                    goToNeowBoss(__instance);
                    return SpireReturn.Return(null);
                }
            }
        }
        return SpireReturn.Continue();
    }

    private static void goToNeowBoss(ProceedButton __instance) {
        AbstractDungeon.bossKey = NeowBossFinal.ID;
        CardCrawlGame.music.fadeOutBGM();
        CardCrawlGame.music.fadeOutTempBGM();
        MapRoomNode node = new MapRoomNode(-2, 5);
        node.room = new MonsterRoomBoss();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        __instance.hide();
    }
}
