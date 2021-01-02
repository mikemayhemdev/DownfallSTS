/*
package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import sneckomod.cards.unknowns.AbstractUnknownCard;

@SpirePatch(
        clz = CardGroup.class,
        method = "getGroupWithoutBottledCards"
)
public class UnknownNoBottlePatch {
    public static CardGroup Postfix(CardGroup __param, CardGroup __result) {
        __result.group.removeIf(c -> c instanceof AbstractUnknownCard);
        return __result;
    }
}
*/