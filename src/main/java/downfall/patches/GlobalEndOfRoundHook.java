package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import guardian.stances.DefensiveMode;

@SpirePatch(
        clz = MonsterGroup.class,
        method = "applyEndOfTurnPowers"
)
public class GlobalEndOfRoundHook {
    public static void Postfix(MonsterGroup __instance) {
        if (AbstractDungeon.player.stance instanceof DefensiveMode) {
            ((DefensiveMode) AbstractDungeon.player.stance).onEndOfRound();
        }
    }
}