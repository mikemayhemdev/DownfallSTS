package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import downfall.downfallMod;
import theHexaghost.relics.TheBrokenSeal;

import java.util.ArrayList;

import static downfall.patches.DeathScreenScoreBonusesPatch.uiStrings2;

public class ReplaceHeartKillBonusPatch {

    public static String CHECKAGAINST = CardCrawlGame.languagePack.getScoreString("Heartbreaker").NAME;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("WhaleHunter"));

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
                stats.add(idx, new GameOverStat(uiStrings.TEXT[0], uiStrings.TEXT[1], Integer.toString(250)));
            }
                     //   ArrayList<GameOverStat> stats2 = ReflectionHacks.getPrivate(__instance, GameOverScreen.class, "stats");
                     //   if (AbstractDungeon.player.hasRelic(TheBrokenSeal.ID)) {
                      //      stats2.add(stats2.size()-2, new GameOverStat(uiStrings2.TEXT[0], uiStrings2.TEXT[1], Integer.toString(333)));
                    //    }

                    }
                }
            }
