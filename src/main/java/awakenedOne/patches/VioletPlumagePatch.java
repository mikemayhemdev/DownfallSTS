package awakenedOne.patches;
//

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.powers.EnsorcelatePower;
import awakenedOne.powers.IntensifyPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.relics.BlackCandle;
//
////This is code from the Aspiration mod!
////https://github.com/erasels/Aspiration-StS/blob/564c18fce53f30b105fc401fd22445aed018d178/src/main/java/aspiration/patches/cards/CardCostModificationPatches.java#L3

public class VioletPlumagePatch {
    private static boolean isIndeedWithoutADoubtInCombat() {
        return (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }

    @SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
    public static class FreeToPlayPatch {
        @SpirePostfixPatch
        public static boolean patch(boolean __result, AbstractCard __instance) {
            if (__result)
                return true;

            if (isIndeedWithoutADoubtInCombat() && __instance.type == AbstractCard.CardType.CURSE) {
                if (__instance.cost != 0) {
                    return (AbstractDungeon.player.hasRelic(BlackCandle.ID));
                }
            }

            if (isIndeedWithoutADoubtInCombat() && __instance instanceof AbstractSpellCard && (AbstractDungeon.player.hasPower(IntensifyPower.POWER_ID))) {
                return (AbstractDungeon.player.hasPower(IntensifyPower.POWER_ID));
            }

            if (isIndeedWithoutADoubtInCombat() && __instance.type == AbstractCard.CardType.POWER && (AbstractDungeon.player.hasPower(EnsorcelatePower.POWER_ID))) {
                return (AbstractDungeon.player.hasPower(EnsorcelatePower.POWER_ID));
            }
            return __result;
        }
    }
}