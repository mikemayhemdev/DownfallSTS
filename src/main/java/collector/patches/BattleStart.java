package collector.patches;

import collector.cards.Bellow;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfCombatPreDrawLogic"
)
public class BattleStart {
    public static void Prefix(AbstractPlayer __instance) {
        Bellow.BLOCK_AMT_LOST = 0;
    }
}