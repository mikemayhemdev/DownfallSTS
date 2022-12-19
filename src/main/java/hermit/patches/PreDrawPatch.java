package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import hermit.powers.EternalPower;

public class PreDrawPatch {
    public static boolean DRAWN_DURING_TURN = false;

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class AbstractPlayerApplyStartOfTurnRelicsPatch {
        public static void Prefix(AbstractPlayer __instance) {
            EternalPower.total = 4;
            DRAWN_DURING_TURN = false;
        }
    }
}