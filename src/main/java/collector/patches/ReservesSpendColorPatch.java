package collector.patches;

import collector.util.NewReserves;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class ReservesSpendColorPatch {
    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SpecialColor {

        private static final Color SPENDING_RESERVES_COLOR = new Color(1.0F, 0.8F, 0.5F, 1);

        @SpireInsertPatch(locator = Locator.class, localvars = {"costColor"})
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef Color costColor[]) {
            if (__instance.costForTurn > EnergyPanel.totalCount && __instance.costForTurn <= EnergyPanel.totalCount + NewReserves.reserveCount() && !__instance.freeToPlay()) {
                costColor[0] = SPENDING_RESERVES_COLOR.cpy();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "transparency");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

}
