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
                if (GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame) {
                    for (int j = GhostflameHelper.hexaGhostFlames.size() - 1; j >= 0; j--) {
                        AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(j);
                        if (gf.charged) {
                            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));  //Critical for keeping the UI not broken, and helps sell the anim
                            AbstractDungeon.actionManager.addToBottom(new ExtinguishAction(gf));
                        }
                    }
                }
                if (AbstractDungeon.player.hasPower(AgainPower.POWER_ID)) {
                    AbstractPower p = AbstractDungeon.player.getPower(AgainPower.POWER_ID);
                    p.flash();
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p.owner, p.owner, p, 1));
                } else if (AbstractDungeon.player.hasPower(StopFromAdvancingPower.POWER_ID)) {
                    AbstractPower p = AbstractDungeon.player.getPower(StopFromAdvancingPower.POWER_ID);
                    p.flash();

                } else if (GhostflameHelper.activeGhostFlame.charged) {
                    AbstractDungeon.actionManager.addToBottom(new AdvanceAction(true));

                }
            }
            downfallMod.playedBossCardThisTurn = false;
        }
    }
}