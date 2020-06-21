/*
package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.SoundMaster;

@SpirePatch(clz = SoundMaster.class, method = "playV")
public class GoldSoundPatch {
    public static SpireReturn Prefix(SoundMaster __instance, String key, float volumemod) {
        if (key.equals("GOLD_GAIN") || key.equals("GOLD_GAIN_2") || key.equals("GOLD_GAIN_3") || key.equals("GOLD_GAIN_4") || key.equals("GOLD_GAIN_5") || key.equals("GOLD_JINGLE")) {
            __instance.playV("soulVFX", volumemod);
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
*/