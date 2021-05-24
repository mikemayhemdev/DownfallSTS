package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class AllyTransitionPatch {
    @SpirePatch(clz = AbstractDungeon.class, method = "resetPlayer")
    public static class RoomTransitionPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (AbstractDungeon.player instanceof CollectorChar) {
                TorchChar dragon = CollectorChar.getDragon();
                dragon.loseBlock(true);
                if (!dragon.stance.ID.equals(NeutralStance.STANCE_ID)) {
                    dragon.stance = new NeutralStance();
                    dragon.onStanceChange(NeutralStance.STANCE_ID);
                }
            }
        }
    }
}
