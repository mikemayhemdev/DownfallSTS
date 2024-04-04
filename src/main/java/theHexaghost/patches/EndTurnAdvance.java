package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.MayhemGhostflame;
import theHexaghost.powers.AgainPower;
import theHexaghost.powers.CrispyPower_new;
import theHexaghost.powers.StopFromAdvancingPower;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class EndTurnAdvance {
    public static void Postfix(GameActionManager __instance) {
        if (HexaMod.renderFlames) {
            if (GhostflameHelper.activeGhostFlame instanceof MayhemGhostflame)
                GhostflameHelper.activeGhostFlame.advanceTrigger(null);
            if (GhostflameHelper.activeGhostFlame.charged) {
//                if ( GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame ) {
//                    for (int j = GhostflameHelper.hexaGhostFlames.size() - 1; j >= 0; j--) {
//                        AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(j);
//                        if (gf.charged) {
//                            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));  //Critical for keeping the UI not broken, and helps sell the anim
//                            AbstractDungeon.actionManager.addToBottom(new ExtinguishAction(gf));
//                        }
//                    }
//                }

                if(AbstractDungeon.player.hasPower(CrispyPower_new.POWER_ID)){
                    if (AbstractDungeon.player.hasPower(StopFromAdvancingPower.POWER_ID)) {
                        GhostflameHelper.activeGhostFlame.extinguish();
                    }
                    AbstractDungeon.player.getPower(CrispyPower_new.POWER_ID).onSpecificTrigger();
                }

                else if (AbstractDungeon.player.hasPower(StopFromAdvancingPower.POWER_ID)) {
                    GhostflameHelper.activeGhostFlame.extinguish();
                }

                if (GhostflameHelper.activeGhostFlame.charged) {
                    AbstractDungeon.actionManager.addToBottom(new AdvanceAction(true));
                }
            }

            if(GhostflameHelper.hexaGhostFlames.get(5) instanceof InfernoGhostflame) { // only auto extinguish inferno when it's not replaced to others
                GhostflameHelper.hexaGhostFlames.get(5).extinguish();
            }
        }
        downfallMod.playedBossCardThisTurn = false;
    }
}