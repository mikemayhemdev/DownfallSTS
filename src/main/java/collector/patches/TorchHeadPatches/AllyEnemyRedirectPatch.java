package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class AllyEnemyRedirectPatch {
    @SpirePatch(clz = DamageAction.class, method = "stealGold")
    public static class DragonGoldStolenPatch {
        static AbstractCreature prevTarget = null;

        @SpirePrefixPatch
        public static void Prefix(DamageAction __instance) {
            TorchChar dragon = CollectorChar.getTorchHead();
            if (dragon != null && __instance.target == dragon) {
                prevTarget = __instance.target;
                __instance.target = AbstractDungeon.player;
            }
        }

        @SpireInsertPatch(locator = AfterStealGoldLocator.class)
        public static void Insert(DamageAction __instance) {
            if (prevTarget != null) {
                __instance.target = prevTarget;
                prevTarget = null;
            }
        }

        @SpirePostfixPatch
        public static void Postfix(DamageAction __instance) {
            Insert(__instance);
        }
    }

    public static class AfterStealGoldLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(DamageAction.class, "goldAmount");
            int[] all = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{all[all.length - 1]};
        }
    }
}
