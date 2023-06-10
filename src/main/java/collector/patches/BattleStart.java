package collector.patches;

import collector.cards.DarkGaze;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfCombatPreDrawLogic"
)
public class BattleStart {
    public static void Prefix(AbstractPlayer __instance) {
        DarkGaze.BLOCK_AMT_LOST = 0;
    }
}