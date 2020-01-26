package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class ReturnStasisCardToHandAction extends AbstractGameAction {
    private AbstractCard card;

    public ReturnStasisCardToHandAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractDungeon.player.hand.addToHand(this.card);
        this.card.update();
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}
