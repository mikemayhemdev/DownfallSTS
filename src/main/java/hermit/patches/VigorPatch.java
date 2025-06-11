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

import static champ.ChampMod.vigor;


public class VigorPatch {
    public static int  isActive=0;
    public static boolean thisRun=false;

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class DisableHealing
    {
        @SpirePrefixPatch
        public static void Prefix(GameActionManager m)
        {
            if (m.actions.isEmpty() && m.preTurnActions.isEmpty() && m.cardQueue.isEmpty() && isActive > 0) {
                VigorPatch.thisRun = true;
            }
        }
        @SpirePostfixPatch
        public static void Postfix(GameActionManager m)
        {
            if (thisRun) {
                System.out.println("Is thisrun");
                while (isActive > 0) {
                    if (AbstractDungeon.player.hasPower(BigShotPower.POWER_ID)) {

                        AbstractPlayer p = AbstractDungeon.player;
                        AbstractPower pow = AbstractDungeon.player.getPower(BigShotPower.POWER_ID);
                        pow.flash();
                        vigor(pow.amount);
                        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, pow.amount), pow.amount));
                    }
                    thisRun = false;
                    isActive -= 1;
                }
            }

        }
    }


}
