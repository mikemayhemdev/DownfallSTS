package awakenedOne.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface onGenerateCardMidcombatInterface {
    default void onCreateCard(AbstractCard card) {
    }

    default void onCreateThisCard() {
    }
}
