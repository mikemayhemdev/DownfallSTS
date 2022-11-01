package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShivPerCardPlayedAction extends AbstractGameAction {
    private final boolean upgraded;

    public ShivPerCardPlayedAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        this.isDone = true;
        int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        --count;
        AbstractCard c = new Shiv();
        if (upgraded) {
            c.upgrade();
        }
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c, count));
    }
}

