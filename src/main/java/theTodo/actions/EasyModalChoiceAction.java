package theTodo.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class EasyModalChoiceAction extends SelectCardsAction {

    //TODO: When the StsLib SelectCardsCenteredAction PR is pushed to steam, swap the extended action with that.

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount, String textforSelect) {
        super(list, amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        });
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount) {
        this(list, amount, "Choose.");
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list) {
        this(list, 1, "Choose.");
    }
}
