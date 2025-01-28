package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import downfall.downfallMod;
import theHexaghost.relics.TheBrokenSeal;

import java.util.ArrayList;

public class DeathScreenScoreBonusesPatch {

    public static String CHECKAGAINST = CardCrawlGame.languagePack.getScoreString("Heartbreaker").NAME;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("WhaleHunter"));
    public static final UIStrings uiStrings2 = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("Unfettered"));

    @SpirePatch(
            clz = DeathScreen.class,
            method = "createGameOverStats"
    )
    //use replaceheartkillbonuspatch too so it also counts on winning a run.
    public static class DeathScreenPatch {
        public static void Postfix(DeathScreen __instance) {
            if (EvilModeCharacterSelect.evilMode) {
                ArrayList<GameOverStat> stats = ReflectionHacks.getPrivate(__instance, GameOverScreen.class, "stats");
                //insert evil mode score bonuses here.
            }


            //insert non evil exclusive score bonuses here
          //  ArrayList<GameOverStat> stats2 = ReflectionHacks.getPrivate(__instance, GameOverScreen.class, "stats");
          //  if (AbstractDungeon.player.hasRelic(TheBrokenSeal.ID)) {
            //    stats2.add(stats2.size()-2, new GameOverStat(uiStrings2.TEXT[0], uiStrings2.TEXT[1], Integer.toString(333)));
          //  }
        }
    }
}