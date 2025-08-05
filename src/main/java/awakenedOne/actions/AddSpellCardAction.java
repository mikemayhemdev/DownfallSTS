package awakenedOne.actions;

import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AddSpellCardAction extends AbstractGameAction {
    private final AbstractCard cardToAdd;

    public AddSpellCardAction(AbstractCard cardToAdd) {
        this.cardToAdd = cardToAdd;
    }

    @Override
    public void update() {
        isDone = true;
        OrbitingSpells.addSpellCard(cardToAdd);
    }
}
