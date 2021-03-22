package reskinContent.patches;

import automaton.AutomatonChar;
import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import guardian.patches.GuardianEnum;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkinCharacter;
import reskinContent.vfx.ReskinUnlockedTextEffect;
import slimebound.patches.SlimeboundEnum;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

public class VictoryScreenPatches {
    @SpirePatch
            (clz = VictoryScreen.class,
                    method = "updateAscensionAndBetaArtProgress"
            )
    public static class ReskinUnlockPatch {

        @SpirePrefixPatch
        public static void Prefix(VictoryScreen _instance) {
            if (AbstractDungeon.isAscensionMode && !Settings.seedSet && !Settings.isTrial) {
            } else {
                for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                    c.checkUnlock();
                }
                reskinContent.saveSettings();
            }
            SpireReturn.Continue();
        }
    }
}
