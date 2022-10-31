package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

//Thanks Mystic mod
public class SetCardTargetCoordinatesAction extends AbstractGameAction {
    private AbstractCard card;
    private float destinationX;
    private float destinationY;

    public SetCardTargetCoordinatesAction(AbstractCard card, float destinationX, float destinationY) { //set a coordinate to -1 to use original coordinate
        this.actionType = ActionType.SPECIAL;
        this.card = card;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    @Override
    public void update() {
        if (destinationX != -1.0f) {
            card.target_x = destinationX;
        }
        if (destinationY != -1.0f) {
            card.target_y = destinationY;
        }
        isDone = true;
    }
}
