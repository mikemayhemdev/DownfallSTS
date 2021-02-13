package downfall.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import downfall.downfallMod;

@SpirePatch(
        clz = CharacterSelectScreen.class,
        method = "render"
)
public class AscensionTextPatch {

      private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("AscensionText"));

    public static void Prefix(CharacterSelectScreen __instance, SpriteBatch sb) {
        if (EvilModeCharacterSelect.evilMode) {
            if (__instance.ascensionLevel == 20) {
                __instance.ascLevelInfoString = uiStrings.TEXT[1];
            } else if (__instance.ascensionLevel == 19) {
                __instance.ascLevelInfoString = uiStrings.TEXT[0];
            } else if (__instance.ascensionLevel == 4) {
                __instance.ascLevelInfoString = uiStrings.TEXT[3];
            }
        }
    }
}
