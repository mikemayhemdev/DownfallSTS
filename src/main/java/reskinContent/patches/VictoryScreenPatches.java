package reskinContent.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkinCharacter;

public class VictoryScreenPatches {
    @SpirePatch
            (clz = VictoryScreen.class,
                    method = "updateAscensionAndBetaArtProgress"
            )
    public static class ReskinUnlockPatch {

        @SpirePrefixPatch
        public static void Prefix(VictoryScreen _instance) {
            System.out.println("================您跑了吗");
            if (!Settings.seedSet && !Settings.isTrial) {
                System.out.println("================您跑了吗2");
                for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                    c.checkUnlock();
                }
                reskinContent.saveSettings();
            }
            SpireReturn.Continue();
        }
    }
}
