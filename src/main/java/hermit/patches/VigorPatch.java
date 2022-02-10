package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hermit.powers.BigShotPower;


public class VigorPatch {
    public static boolean isActive=false;
    public static boolean thisRun=false;

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class DisableHealing
    {
        @SpirePrefixPatch
        public static void Prefix(GameActionManager m)
        {
            if (m.actions.isEmpty() && m.preTurnActions.isEmpty() && m.cardQueue.isEmpty() && isActive) {

                VigorPatch.thisRun = true;
            }
        }
        @SpirePostfixPatch
        public static void Postfix(GameActionManager m)
        {
            if (thisRun) {
                System.out.println("Is thisrun");
                if (AbstractDungeon.player.hasPower(BigShotPower.POWER_ID)) {

                    AbstractPlayer p = AbstractDungeon.player;
                    AbstractPower pow = AbstractDungeon.player.getPower(BigShotPower.POWER_ID);
                    pow.flash();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, pow.amount), pow.amount));
                }
                thisRun=false;
                isActive=false;
            }

        }
    }


}
