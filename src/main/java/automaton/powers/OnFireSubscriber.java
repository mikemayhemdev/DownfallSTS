package automaton.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnFireSubscriber {
    int onFire(AbstractCard toFire, int reps); // Triggers before a card is fired. Return the number of repetitions, in case power modifies. Rest of modifications can be done to the card
}
