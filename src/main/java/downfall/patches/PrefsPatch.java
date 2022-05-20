package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Prefs;

@SpirePatch(
        clz = Prefs.class,
        method = "getString",
        paramtypez = {String.class, String.class}
)
public class PrefsPatch {
    public static boolean depth = false;

    public static SpireReturn<String> Prefix(Prefs __instance, String key, String de) {
        if (key.equals("LANGUAGE") && !depth) {
            depth = true;
            String result = __instance.getString("LANGUAGE", Settings.GameLanguage.ENG.name());
            depth = false;
            if (result.equals(Settings.GameLanguage.ZHT.name())) {
                return SpireReturn.Return(Settings.GameLanguage.ENG.name());
            }
        }
        return SpireReturn.Continue();
    }

}