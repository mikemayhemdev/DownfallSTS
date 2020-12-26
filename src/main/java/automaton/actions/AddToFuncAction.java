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
        if (container != null)
            container.removeCard(myCard);
        FunctionHelper.addToSequence(myCard);
    }
}
