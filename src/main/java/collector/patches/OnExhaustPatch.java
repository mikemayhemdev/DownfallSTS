package collector.patches;

import collector.cards.AshesAndDust;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CtBehavior;

@SpirePatch(
        clz = CardGroup.class,
        method = "moveToExhaustPile",
        paramtypez = {
                AbstractCard.class,
        }

)

public class OnExhaustPatch {
    @SpireInsertPatch(locator = OnExhaustPatch.Locator.class)
    public static void TriggerOnExhaust(CardGroup instance, AbstractCard c) {
        AshesAndDust.exhaustedThisTurn = true;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnExhaust");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}