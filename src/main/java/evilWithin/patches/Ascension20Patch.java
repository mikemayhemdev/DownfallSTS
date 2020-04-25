package evilWithin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

@SpirePatch(
        clz = CharacterSelectScreen.class,
        method = "render"
)
public class Ascension20Patch {
    public static void Prefix(CharacterSelectScreen __instance, SpriteBatch sb) {
        if (__instance.ascensionLevel == 20 && EvilModeCharacterSelect.evilMode) {
            __instance.ascLevelInfoString = "20. Act 3 Boss has Lizard Tail and Magic Flower.";
        }
    }
}
