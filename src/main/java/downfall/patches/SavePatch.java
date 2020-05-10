package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import downfall.downfallMod;

public class SavePatch {
    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "save"
    )
    public static class SaveGame {
        public static void Prefix(SaveFile save) {
            downfallMod.saveData();
        }
    }
}