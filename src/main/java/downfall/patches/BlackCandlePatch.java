package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.relics.BlackCandle;


public class BlackCandlePatch {
    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class MakeActuallyUsable {
        public static boolean Postfix(boolean __result, AbstractCard __instance) {

            if ((__instance.type == AbstractCard.CardType.CURSE) && (__instance.cost == -2)) {
                if (AbstractDungeon.player.hasRelic(BlackCandle.ID)) {
                    return true;
                }
            }
            return (__result);
        }
    }

}