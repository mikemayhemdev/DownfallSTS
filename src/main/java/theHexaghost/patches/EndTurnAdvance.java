package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.MayhemGhostflame;
import theHexaghost.powers.StopFromAdvancingPower;

import java.util.ArrayList;


public class EndTurnAdvance {

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endTurn"
    )
    public static class HereAndNowApplication {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractRoom __instance) {
            if (AbstractDungeon.player.hasPower(StopFromAdvancingPower.POWER_ID)) {
                GhostflameHelper.activeGhostFlame.extinguish();
            }
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");

                return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
            }
        }
    }

    @SpirePatch(
            clz = GameActionManager.class,
            method = "callEndOfTurnActions"
    )
    public static class AutoAdvance {
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
        public static void Postfix(GameActionManager __instance) {
            if (HexaMod.renderFlames) {
                if (GhostflameHelper.activeGhostFlame instanceof MayhemGhostflame)
                    GhostflameHelper.activeGhostFlame.advanceTrigger(null);

                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (GhostflameHelper.activeGhostFlame.charged) {
                            AbstractDungeon.actionManager.addToBottom(new AdvanceAction(true));
                        }
                        this.isDone = true;
                    }
                });

                if(GhostflameHelper.hexaGhostFlames.get(5) instanceof InfernoGhostflame) { // only auto extinguish inferno when it's not replaced by others
                    AbstractDungeon.actionManager.addToBottom(new ExtinguishAction(GhostflameHelper.hexaGhostFlames.get(5)));
                }
            }
            downfallMod.playedBossCardThisTurn = false;
        }
    }

//    public static class Locator extends SpireInsertLocator {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
//
//            return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]+1};
//        }
//    }
}