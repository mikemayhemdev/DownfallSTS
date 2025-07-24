package awakenedOne.actions;

import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveSpellCardActionSpecial extends AbstractGameAction {
    private final AbstractCard card;

    public RemoveSpellCardActionSpecial(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        isDone = true;
        OrbitingSpells.removeSpellCardSpecial(card);
    }
}
