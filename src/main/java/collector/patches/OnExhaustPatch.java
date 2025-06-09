package collector.patches;

import awakenedOne.cards.OnOtherCardExhaustInDiscard;
import collector.cards.OnOtherCardExhaustInHand;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

@SpirePatch(
        clz = CardGroup.class,
        method = "moveToExhaustPile",
        paramtypez = {
                AbstractCard.class,
        }

)

public class OnExhaustPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void TriggerOnExhaust(CardGroup instance, AbstractCard c) {
        for (AbstractCard other : AbstractDungeon.player.hand.group) {
            if (other instanceof OnOtherCardExhaustInHand) {
                ((OnOtherCardExhaustInHand) other).onOtherCardExhaustWhileInHand(c);
            }
        }
        for (AbstractCard other : AbstractDungeon.player.discardPile.group) {
            if (other instanceof OnOtherCardExhaustInDiscard) {
                ((OnOtherCardExhaustInDiscard) other).onOtherCardExhaustWhileInDiscard(c);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnExhaust");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}