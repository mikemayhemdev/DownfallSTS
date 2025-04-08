package collector.patches.PyrePatches;/*
package collector.patches.PyrePatches;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import collector.cardmods.PyreMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpirePatch(clz = SingleCardViewPopup.class, method = "renderTips")
public class PyreTooltips2 {

    @SpireInsertPatch(locator = LocatorAfter.class, localvars = {"card", "t"})
    public static void InsertAfter(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard acard, @ByRef ArrayList<PowerTip>[] t) {
        List<TooltipInfo> tooltips = new ArrayList<>();
        if (CardModifierManager.hasModifier(acard, PyreMod.ID)) {
            tooltips.add(new TooltipInfo(PyreTooltips1.UI_STRINGS.TEXT[0], PyreTooltips1.UI_STRINGS.TEXT[1]));
        }
        if (!tooltips.isEmpty())
            t[0].addAll(tooltips.stream().map(TooltipInfo::toPowerTip).collect(Collectors.toList()));
    }

    private static class LocatorAfter extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
            return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
        }
    }
}
*/