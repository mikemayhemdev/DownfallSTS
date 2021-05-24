package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class AllyEndofRoundPowerPatch {
    @SpirePatch(clz = MonsterGroup.class, method = "applyEndOfTurnPowers")
    public static class EndOfRoundPower {
        @SpireInsertPatch(locator = EndOfTurnPowerLocator.class)
        public static void Insert(MonsterGroup __instance) {
            TorchChar dragon = CollectorChar.getLivingDragon();
            if (dragon != null) {
                for (AbstractPower p : dragon.powers) {
                    p.atEndOfRound();
                }
            }
        }
    }

    private static class EndOfTurnPowerLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(MonsterGroup.class, "monsters");
            int[] all = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{all[1]};
        }
    }
}