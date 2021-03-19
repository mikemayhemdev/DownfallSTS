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

                if(AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN && !reskinContent.guardianReskinUnlock)
                    unlockedReskin = 0;

                if(AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND && !reskinContent.slimeReskinUnlock)
                    unlockedReskin = 1;

                if(AbstractDungeon.player.chosenClass == TheHexaghost.Enums.THE_SPIRIT && !reskinContent.hexaghostReskinUnlock)
                    unlockedReskin = 2;

                if(AbstractDungeon.player.chosenClass == TheSnecko.Enums.THE_SNECKO && !reskinContent.sneckoReskinUnlock)
                    unlockedReskin = 3;

                if(AbstractDungeon.player.chosenClass == ChampChar.Enums.THE_CHAMP && !reskinContent.champReskinUnlock)
                    unlockedReskin = 4;

                if(AbstractDungeon.player.chosenClass == AutomatonChar.Enums.THE_AUTOMATON && !reskinContent.bronzeReskinUnlock)
                    unlockedReskin = 4;

                if(unlockedReskin != -1){
                    AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(unlockedReskin));


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
                        case 4:
                            reskinContent.champReskinUnlock = true;
                            break;
                        case 5:
                            reskinContent.bronzeReskinUnlock = true;
                            break;
                        default:
                            break;
                    }
                    reskinContent.saveSettings();
                    System.out.println("==============解锁！！！====="+unlockedReskin);
                    System.out.println(reskinContent.guardianReskinUnlock);
                    System.out.println(reskinContent.slimeReskinUnlock);
                    System.out.println(reskinContent.hexaghostReskinUnlock);
                    System.out.println(reskinContent.sneckoReskinUnlock);
                    System.out.println(reskinContent.champReskinAnimation);
                    System.out.println(reskinContent.bronzeReskinAnimation);
                    System.out.println(CardCrawlGame.saveSlot);

                }
            }
            SpireReturn.Continue();
        }
    }
}
