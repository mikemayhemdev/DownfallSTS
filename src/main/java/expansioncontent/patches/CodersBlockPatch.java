package expansioncontent.patches;

import automaton.AutomatonChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.util.DownfallAchievementUnlocker;
import expansioncontent.util.DownfallAchievementVariables;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "onBossVictoryLogic"
)
public class CodersBlockPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractMonster __instance) {
        if (AbstractDungeon.player instanceof AutomatonChar) {
            if (!DownfallAchievementVariables.functionCreated) {
                DownfallAchievementUnlocker.unlockAchievement("CODERS_BLOCK");
            }
        }
    }
}