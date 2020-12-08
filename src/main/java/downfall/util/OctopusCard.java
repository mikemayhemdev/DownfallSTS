package downfall.util;

import downfall.cards.OctoChoiceCard;

import java.util.ArrayList;

public interface OctopusCard {
    ArrayList<OctoChoiceCard> choiceList();

    void doChoiceStuff(OctoChoiceCard c);
}
