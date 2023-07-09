package collector.patches;

import collector.actions.GainReservesAction;
import collector.cards.FingerOfDeath;
import collector.util.NewReserves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

import static collector.util.Wiz.att;

public class ReservesPatch {
    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CardCostPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> bePlayable(AbstractCard __instance) {
            int found = NewReserves.reserveCount();
            // Kill Special Case
            if (__instance.cardID.equals(FingerOfDeath.ID)) {
                if (found >= __instance.costForTurn || __instance.freeToPlay()) {
                    return SpireReturn.Return(true);
                }
                return SpireReturn.Return(false);
            }
            if (found > 0) {
                if (EnergyPanel.totalCount + found >= __instance.costForTurn) {
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
        public static SpireReturn spendReserves(AbstractPlayer __instance, AbstractCard c) {
            if (NewReserves.reserveCount() > 0 && c.costForTurn > 0) {
                if (c.cardID.equals(FingerOfDeath.ID)) {
                    att(new GainReservesAction(-c.costForTurn));
                    return SpireReturn.Return();
                } else {
                    int delta = c.costForTurn - EnergyPanel.getCurrentEnergy();
                    if (delta > 0) {
                        att(new GainReservesAction(-delta));
                    }
                }
            }
            return SpireReturn.Continue();
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
