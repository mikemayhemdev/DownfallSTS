package guardian.cards;

import guardian.orbs.StasisOrb;

public interface InStasisCard {
    void onStartOfTurn(StasisOrb orb);
    void onEvoke(StasisOrb orb);
}
