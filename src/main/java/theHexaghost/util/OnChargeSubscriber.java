package theHexaghost.util;

import theHexaghost.ghostflames.AbstractGhostflame;

public interface OnChargeSubscriber {
    default void onCharge(AbstractGhostflame chargedFlame) {

    }
}