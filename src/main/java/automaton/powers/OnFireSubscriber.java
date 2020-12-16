package automaton.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnFireSubscriber {
    void onFire(AbstractCard toFire); // Triggers before a card is fired.
}
