package awakenedOne.patches;
//
import awakenedOne.powers.ConjureNextTurnPower;
import awakenedOne.relics.VioletPlumage;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
//
////This is code from the Aspiration mod!
////https://github.com/erasels/Aspiration-StS/blob/564c18fce53f30b105fc401fd22445aed018d178/src/main/java/aspiration/patches/cards/CardCostModificationPatches.java#L3

public class VioletPlumagePatch {
    @SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
    public static class FreeToPlayPatch {
        @SpirePostfixPatch
        public static boolean patch(boolean __result, AbstractCard __instance) {
            if(__result)
                return true;
            if(isIndeedWithoutADoubtInCombat() && __instance.type == AbstractCard.CardType.POWER && (AbstractDungeon.player.hasPower(ConjureNextTurnPower.POWER_ID))) {
                return (AbstractDungeon.player.hasPower(ConjureNextTurnPower.POWER_ID));
            }
            return __result;
        }
    }

    private static boolean isIndeedWithoutADoubtInCombat() {
        return (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }
}