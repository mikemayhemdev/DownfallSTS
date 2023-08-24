package guardian.cards;

import guardian.orbs.StasisOrb;

public interface InStasisCard {
    default void onStartOfTurn(StasisOrb orb) {

    }

    default void onEvoke(StasisOrb orb) {

    }

    default void whenEnteredStasis(StasisOrb orb) {

    }

    default void whenReturnedFromStasis() {

    }
}
