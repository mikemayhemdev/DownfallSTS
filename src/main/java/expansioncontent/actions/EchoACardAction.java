package expansioncontent.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EchoACardAction extends AbstractGameAction {
    private final AbstractCard cardToEcho;

    public EchoACardAction(AbstractCard cardToEcho, int amount, boolean DONT_USE_YET) {
        this.cardToEcho = cardToEcho;
        this.amount = amount;
    }

    @Override
    public void update() {
        isDone = true;
        //TODO: FINISH this.
    }
}
