/*
package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ReplaceHeartKillBonusPatch {

    public static String CHECKAGAINST = CardCrawlGame.languagePack.getScoreString("Heartbreaker").NAME;

    @SpirePatch(
            clz = VictoryScreen.class,
            method = "createGameOverStats"
    )
    public static class VictoryScreenPatch {
        public static void Postfix(VictoryScreen __instance) {
            if (EvilModeCharacterSelect.evilMode) {
                ArrayList<GameOverStat> stats = ReflectionHacks.getPrivate(__instance, GameOverScreen.class, "stats");
                int idx = -1;
                for (int i = 0; i < stats.size(); i++) {
                    if (stats.get(i).label != null) {
                        if (stats.get(i).label.equals(CHECKAGAINST)) {
                            idx = i;
                        }
                    }
                }
                stats.remove(idx);
                stats.add(idx, new GameOverStat("Whale Hunter", "Killed Neow.", "100,000,000,000")); //TODO: Make this legitimate
            }
        }
    }
}
*/