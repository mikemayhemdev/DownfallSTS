package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

@SpirePatch(
        clz = CardGroup.class,
        method = "moveToExhaustPile"
)
public class ExhaustCardTickPatch {
    public static boolean exhaustedLastTurn = false;
    public static boolean exhaustedThisTurn = false;

    public static void Prefix(CardGroup __instance, AbstractCard c) {
        exhaustedThisTurn = true;
    }
}