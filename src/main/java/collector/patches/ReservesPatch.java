package collector.patches;

import collector.powers.ReservePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

import static collector.util.Wiz.att;

public class ReservesPatch {
    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CardCostPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> bePlayable(AbstractCard __instance) {
            AbstractPower power = AbstractDungeon.player.getPower(ReservePower.POWER_ID);
            if (power != null) {
                if (EnergyPanel.totalCount + power.amount >= __instance.costForTurn) {
                    return SpireReturn.Return(true);
                }
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(EnergyPanel.class, "totalCount");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class UseReserves {
        @SpireInsertPatch(locator = Locator.class)
        public static void spendReserves(AbstractPlayer __instance, AbstractCard c) {
            if (__instance.hasPower(ReservePower.POWER_ID) && c.costForTurn > 0) {
                int delta = c.costForTurn - EnergyPanel.getCurrentEnergy();
                if (delta > 0)
                    att(new ReducePowerAction(__instance, __instance, ReservePower.POWER_ID, delta));
            }
        }
        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(EnergyManager.class, "use");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
