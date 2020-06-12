package reskinContent.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import guardian.patches.GuardianEnum;
import reskinContent.reskinContent;
import reskinContent.vfx.ReskinUnlockedTextEffect;
import slimebound.patches.SlimeboundEnum;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

public class VictoryScreenPatches
{


    @SpirePatch
    (clz = VictoryScreen.class,
     method = "updateAscensionAndBetaArtProgress"
   )
    public static class ReskinUnlockPatch{

        @SpirePrefixPatch
        public  static void Prefix(VictoryScreen _instance){
            if (AbstractDungeon.isAscensionMode && !Settings.seedSet && !Settings.isTrial && AbstractDungeon.ascensionLevel < 20 && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs())) {
            }else {
                int unlockedReskin = -1;

                if(AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN)
                    unlockedReskin = 0;

                if(AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND)
                    unlockedReskin = 1;

                if(AbstractDungeon.player.chosenClass == TheHexaghost.Enums.THE_SPIRIT)
                    unlockedReskin = 2;

                if(AbstractDungeon.player.chosenClass == TheSnecko.Enums.THE_SNECKO)
                    unlockedReskin = 3;

                if(unlockedReskin != -1){
                    AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(unlockedReskin));
                    unlockReskin(unlockedReskin);
                    reskinContent.saveSettings();
                }
            }
            SpireReturn.Continue();
        }
    }


    private static void unlockReskin(int unlockedReskin){
        switch (unlockedReskin){
            case 0:
                reskinContent.guardianReskinUnlock = true;
                break;

            case 1:
                reskinContent.slimeReskinUnlock = true;
                break;

            case 2:
                reskinContent.hexaghostReskinUnlock = true;
                break;

            case 3:
                reskinContent.sneckoReskinUnlock = true;
                break;

            default:
                break;
        }
    }
}
