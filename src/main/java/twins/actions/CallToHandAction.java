package twins.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CallToHandAction extends AbstractGameAction {
    private AbstractCard cardToReturn;
    private CardGroup group;

    public CallToHandAction(AbstractCard c, CardGroup group) {
        this.cardToReturn = c;
        this.group = group;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public CallToHandAction(AbstractCard c) {
        this(c, null);
    }

    public void update() {
        if (AbstractDungeon.player.hand.size() >= 10) {
            this.isDone = true;
        } else {
            if (AbstractDungeon.player.hand.size() < 10) {
                AbstractDungeon.player.hand.addToHand(cardToReturn);
                group.removeCard(cardToReturn);
            }

            cardToReturn.lighten(false);
            AbstractDungeon.player.hand.refreshHandLayout();
            this.isDone = true;
        }
        this.tickDuration();
    }
}
