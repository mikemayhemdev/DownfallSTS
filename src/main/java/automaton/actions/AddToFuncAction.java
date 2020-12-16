package automaton.actions;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.actions.SpeechBubbleAction;

public class AddToFuncAction extends AbstractGameAction {
    private AbstractCard myCard;

    public AddToFuncAction(AbstractCard card) {
        myCard = card;
    }

    @Override
    public void update() {
        isDone = true;
        if (FunctionHelper.held.size() < FunctionHelper.max) {
            FunctionHelper.held.add(myCard);
        }
        else {
            //TODO: "CARD_CANNOT_INPUT" dialog
        }
    }
}
