package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import gremlin.relics.ImpeccablePecs;
import gremlin.relics.MagicalMallet;
import javassist.CtBehavior;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class ApplyPowerPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void patch(ApplyPowerAction __instance) {
        if (AbstractDungeon.player.hasRelic(MagicalMallet.ID)) {
            AbstractPower powerToApply = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
            if (__instance.source != null && __instance.source.isPlayer && __instance.target != __instance.source
                    && powerToApply.ID.equals(WeakPower.POWER_ID)
                    && !__instance.target.hasPower("Artifact")) {
                AbstractDungeon.player.getRelic(MagicalMallet.ID).onTrigger(__instance.target);
            }
        }
        if (AbstractDungeon.player.hasRelic(ImpeccablePecs.ID)) {
            AbstractPower powerToApply = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
            if (__instance.target != null && __instance.target.isPlayer && powerToApply.ID.equals(StrengthPower.POWER_ID)) {
                ((ImpeccablePecs)(AbstractDungeon.player.getRelic(ImpeccablePecs.ID))).onTrigger(powerToApply.amount);
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}