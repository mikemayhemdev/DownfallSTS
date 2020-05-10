package downfall.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

@SpirePatch(
        clz = CharacterSelectScreen.class,
        method = "render"
)
public class AscensionTextPatch {
    public static void Prefix(CharacterSelectScreen __instance, SpriteBatch sb) {
        if (EvilModeCharacterSelect.evilMode) {
            if (__instance.ascensionLevel == 20) {
                __instance.ascLevelInfoString = "20. Act 3 Boss has Lizard Tail and Magic Flower.";
            } else if (__instance.ascensionLevel == 19) {
                __instance.ascLevelInfoString = "19. Bosses have an extra Relic.";
            }
        }
    }
}
