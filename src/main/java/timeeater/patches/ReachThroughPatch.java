package timeeater.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getRewardCards"
)
public class ReachThroughPatch {
    public static boolean ignoreNextReward = false;

    public static SpireReturn Prefix() {
        if (ignoreNextReward) {
            ArrayList<AbstractCard> blah = new ArrayList<>();
            ignoreNextReward = false;
            return SpireReturn.Return(blah);
        }
        return SpireReturn.Continue();
    }
}
