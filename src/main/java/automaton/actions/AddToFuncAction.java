package automaton.actions;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class AddToFuncAction extends AbstractGameAction {
    private AbstractCard myCard;
    private CardGroup container;

    public AddToFuncAction(AbstractCard card, CardGroup c) {
        myCard = card;
        container = c;
    }

    @Override
    public void update() {
        isDone = true;
        if (FunctionHelper.held.size() < FunctionHelper.max()) {
            if (container != null)
                container.removeCard(myCard);
            FunctionHelper.addToSequence(myCard);
        } else {
            addToBot(new AddToFuncAction(myCard, container)); // Shoddy but hopefully workable fix.
        }
    }
}
