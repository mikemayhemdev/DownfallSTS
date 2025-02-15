package sneckomod.patches;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.powers.watcher.WaveOfTheHandPower;

//This is AlexMdle's code, not mine.

public class WaveOfTheHandPatch {
    public static int  isActive=0;
    @SpirePatch(clz = WaveOfTheHandPower.class, method = "onGainedBlock")
    public static class AbstractWaveOfTheHandPatch {
        @SpirePrefixPatch
        public static void Prefix(WaveOfTheHandPower __instance, @ByRef float[] blockAmount) {
            if (isActive == 1) {
                blockAmount[0] = 0.0F;
                isActive = 0;
            }
        }
    }
}