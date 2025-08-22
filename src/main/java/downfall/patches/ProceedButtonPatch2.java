
package downfall.patches;


import charbosses.bosses.Merchant.CharBossMerchant;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
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
         //   SlimeboundMod.logger.info("HIT THE DOUBLE BOSS PATCH!");
            AbstractDungeon.bossList.clear();
            AbstractDungeon.bossList.add(CharBossMerchant.ID); // This and clear - just for safety.
            AbstractDungeon.bossKey = CharBossMerchant.ID;
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