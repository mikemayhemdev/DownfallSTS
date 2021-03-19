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
            if (AbstractDungeon.isAscensionMode && !Settings.seedSet && !Settings.isTrial && AbstractDungeon.ascensionLevel < 20 && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs())) {
            } else {
                int unlockedReskin = -1;

                if (AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN && !CharacterSelectScreenPatches.characters[0].reskinUnlock)
                    unlockedReskin = 0;

                if (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND && !CharacterSelectScreenPatches.characters[1].reskinUnlock)
                    unlockedReskin = 1;

                if (AbstractDungeon.player.chosenClass == TheHexaghost.Enums.THE_SPIRIT && !CharacterSelectScreenPatches.characters[2].reskinUnlock)
                    unlockedReskin = 2;

                if (AbstractDungeon.player.chosenClass == TheSnecko.Enums.THE_SNECKO && !CharacterSelectScreenPatches.characters[3].reskinUnlock)
                    unlockedReskin = 3;

                if (AbstractDungeon.player.chosenClass == ChampChar.Enums.THE_CHAMP && !CharacterSelectScreenPatches.characters[4].reskinUnlock)
                    unlockedReskin = 4;

                if (AbstractDungeon.player.chosenClass == AutomatonChar.Enums.THE_AUTOMATON && !CharacterSelectScreenPatches.characters[5].reskinUnlock)
                    unlockedReskin = 5;

                if (unlockedReskin != -1) {
                    AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(unlockedReskin));
                    CharacterSelectScreenPatches.characters[unlockedReskin].reskinUnlock = true;


                    reskinContent.saveSettings();
                    System.out.println("==============解锁！！！=====" + unlockedReskin);
                    for(AbstractSkinCharacter c : CharacterSelectScreenPatches.characters){
                        System.out.println(c.reskinUnlock);
                    }
                    System.out.println(CardCrawlGame.saveSlot);
                }
            }
            SpireReturn.Continue();
        }
    }
}
