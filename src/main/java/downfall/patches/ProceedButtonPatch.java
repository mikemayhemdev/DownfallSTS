package downfall.patches;


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import downfall.monsters.FleeingMerchant;
import downfall.rooms.HeartShopRoom;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = ProceedButton.class,
        method = "update"
)
public class ProceedButtonPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static SpireReturn Insert(ProceedButton __instance) {
        AbstractRoom r = AbstractDungeon.getCurrRoom();
        if (r instanceof HeartShopRoom) {
            if ((((HeartShopRoom) r).startedCombat && FleeingMerchant.DEAD) || FleeingMerchant.ESCAPED) {
                AbstractRoom tRoom = new HeartShopRoom(false);
                tRoom.rewards.clear();
                AbstractDungeon.combatRewardScreen.clear();
                AbstractDungeon.currMapNode.setRoom(tRoom);
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.scene.nextRoom(tRoom);
                CardCrawlGame.fadeIn(1.5F);
                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                tRoom.onPlayerEntry();
                AbstractDungeon.closeCurrentScreen();
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "closeCurrentScreen");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[2]};
        }
    }
}