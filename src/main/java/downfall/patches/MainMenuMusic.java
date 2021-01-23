package downfall.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import downfall.downfallMod;

@SpirePatch(clz = MainMusic.class, method = "getSong")
public class MainMenuMusic {
    @SpirePostfixPatch
    public static Music Postfix(Music __result, MainMusic __instance, String key) {
        if (!downfallMod.noMusic) {
            if ("MENU".equals(key)) {
                return MainMusic.newMusic("downfallResources/music/MenuMusic.mp3");
            }
        }
        return __result;
    }
}