package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.SoundMaster;

public class GoldSoundPatch {

    @SpirePatch(clz = SoundMaster.class, method = "playV", paramtypez = {String.class, float.class})
    public static class GoldSoundPatchV {
        public static SpireReturn Prefix(SoundMaster __instance, String key, float volumemod) {
            String s = findReplacement(key, true);
            if (s == "") {
                return SpireReturn.Continue();
            } else {
                __instance.playV(s, volumemod);
                return SpireReturn.Return(bruh());
            }
        }
    }

    @SpirePatch(clz = SoundMaster.class, method = "playA", paramtypez = {String.class, float.class})
    public static class GoldSoundPatchA {
        public static SpireReturn Prefix(SoundMaster __instance, String key, float pitchAdjust) {
            String s = findReplacement(key, true);
            if (s == "") {
                return SpireReturn.Continue();
            } else {
                __instance.playA(s, pitchAdjust);
                return SpireReturn.Return(bruh());
            }
        }
    }

    @SpirePatch(clz = SoundMaster.class, method = "play", paramtypez = {String.class})
    public static class GoldSoundPatchNormal {
        public static SpireReturn Prefix(SoundMaster __instance, String key) {
            String s = findReplacement(key, true);
            if (s == "") {
                return SpireReturn.Continue();
            } else {
                __instance.play(s);
                return SpireReturn.Return(bruh());
            }
        }
    }


    @SpirePatch(clz = SoundMaster.class, method = "play", paramtypez = {String.class, boolean.class})
    public static class GoldSoundPatchNormal2 {
        public static SpireReturn Prefix(SoundMaster __instance, String key, boolean useBgmVolume) {
            String s = findReplacement(key, true);
            if (s == "") {
                return SpireReturn.Continue();
            } else {
                __instance.play(s, useBgmVolume);
                return SpireReturn.Return(bruh());
            }
        }
    }


    @SpirePatch(clz = SoundMaster.class, method = "play", paramtypez = {String.class, float.class})
    public static class GoldSoundPatchNormal3 {
        public static SpireReturn Prefix(SoundMaster __instance, String key, float pitchVariation) {
            String s = findReplacement(key, true);
            if (s == "") {
                return SpireReturn.Continue();
            } else {
                __instance.play(s, pitchVariation);
                return SpireReturn.Return(bruh());
            }
        }
    }


    @SpirePatch(clz = SoundMaster.class, method = "playAV", paramtypez = {String.class, float.class, float.class})
    public static class GoldSoundPatchAV {
        public static SpireReturn Prefix(SoundMaster __instance, String key, float pitchAdjust, float volumeMod) {
            String s = findReplacement(key, true);
            if (s == "") {
                return SpireReturn.Continue();
            } else {
                __instance.playAV(s, pitchAdjust, volumeMod);
                return SpireReturn.Return(bruh());
            }
        }
    }

    public static long bruh() {
        return 0;
    }

    public static String findReplacement(String key, boolean check) {
        if (check)
            System.out.println(findReplacement(key, false));
        if (key.equals("GOLD_GAIN")) {
            return "souls1";
        } else if (key.equals("GOLD_GAIN_2")) {
            return "souls2";
        } else if (key.equals("GOLD_GAIN_3")) {
            return "souls3";
        } else if (key.equals("GOLD_GAIN_4")) {
            return "souls4";
        } else if (key.equals("GOLD_GAIN_5")) {
            return "souls5";
        } else if (key.equals("GOLD_JINGLE")) {
            return "soulsMain";
        }
        return "";
    }

}
