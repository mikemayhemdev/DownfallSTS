package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;


@SpirePatch(
        clz = ReturnToMenuButton.class,
        method = "appear",
        paramtypez = {float.class, float.class, String.class}
)

public class DeathScreenCharUnlockPatch {
    @SpirePostfixPatch
    public static void Postfix(ReturnToMenuButton __instance, float x, float y, String label) {

        String[] TEXT = CardCrawlGame.languagePack.getUIString("DeathScreen").TEXT;

        if (label == TEXT[37]) {
            //////downfallMod.logger.info("text patch fired");
            if (UnlockTracker.isCharacterLocked("Guardian")) {
                //   //downfallMod.logger.info("first if");
                __instance.appear(Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.15F, TEXT[40]);
                __instance.label = TEXT[40];
            } else if (UnlockTracker.isCharacterLocked("Hexaghost") && EvilModeCharacterSelect.evilMode && AbstractDungeon.player.chosenClass == downfallMod.Enums.GUARDIAN) {
                //  //downfallMod.logger.info("second if");
                __instance.appear(Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.15F, TEXT[40]);
                __instance.label = TEXT[40];
            } else if (UnlockTracker.isCharacterLocked("Champ") && EvilModeCharacterSelect.evilMode && AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_SPIRIT) {
                //  //downfallMod.logger.info("second if");
                __instance.appear(Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.15F, TEXT[40]);
                __instance.label = TEXT[40];
            } else if (UnlockTracker.isCharacterLocked("Automaton") && EvilModeCharacterSelect.evilMode && AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_CHAMP) {
                //  //downfallMod.logger.info("second if");
                __instance.appear(Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.15F, TEXT[40]);
                __instance.label = TEXT[40];
            } else if (UnlockTracker.isCharacterLocked("Gremlin") && EvilModeCharacterSelect.evilMode && AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_AUTOMATON) {
                //  //downfallMod.logger.info("second if");
                __instance.appear(Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.15F, TEXT[40]);
                __instance.label = TEXT[40];
            } else if ((UnlockTracker.isCharacterLocked("Snecko")) &&
                    !(UnlockTracker.isCharacterLocked("SlimeBoss")) &&
                    !(UnlockTracker.isCharacterLocked("Guardian")) &&
                    !(UnlockTracker.isCharacterLocked("Hexaghost")) &&
                    !(UnlockTracker.isCharacterLocked("Champ")) &&
                    !(UnlockTracker.isCharacterLocked("Automaton")) &&
                    !(UnlockTracker.isCharacterLocked("Gremlin")) &&
                    EvilModeCharacterSelect.evilMode && AbstractDungeon.player.chosenClass == downfallMod.Enums.GREMLIN) {
                //   //downfallMod.logger.info("third if");
                __instance.appear(Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.15F, TEXT[40]);
                __instance.label = TEXT[40];
            }

        }
    }

}



