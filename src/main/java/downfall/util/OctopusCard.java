package downfall.util;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;

import java.util.ArrayList;

public interface OctopusCard {
    ArrayList<OctoChoiceCard> choiceList();

    void doChoiceStuff(AbstractMonster m, OctoChoiceCard c);
}
