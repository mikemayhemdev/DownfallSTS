package gremlin.patches;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UnmovablePatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToBottomOfDeck")
    @SpirePatch(clz = CardGroup.class, method = "resetCardBeforeMoving")
    public static class StaysInHand
    {
        public static void Prefix(CardGroup __instance, AbstractCard card)
        {
            if (card instanceof Unmovable && !((Unmovable) card).canMove)
            {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToTop(new FetchAction(p.exhaustPile, c -> (c == card)));
                AbstractDungeon.actionManager.addToTop(new FetchAction(p.drawPile, c -> (c == card)));
                AbstractDungeon.actionManager.addToTop(new FetchAction(p.discardPile, c -> (c == card)));
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck")
    public static class StaysInHand2
    {
        public static void Prefix(CardGroup __instance, AbstractCard card, boolean randomSpot)
        {
            if (card instanceof Unmovable && !((Unmovable) card).canMove)
            {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToTop(new FetchAction(p.drawPile, c -> (c == card)));
            }
        }
    }
}
