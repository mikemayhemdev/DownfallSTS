package awakenedOne.patches;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = StSLib.class, method = "onCreateCard", paramtypez = {AbstractCard.class})
public class OnCreateCardSubscriber {
    public static int CardsCreatedThisCombat = 0;
    @SpirePostfixPatch
    public static void onCreateCard(AbstractCard c) {
        CardsCreatedThisCombat++;
    }
}