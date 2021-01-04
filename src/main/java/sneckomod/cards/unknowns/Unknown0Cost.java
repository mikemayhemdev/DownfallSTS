package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Unknown0Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown0Cost");

    public Unknown0Cost() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 0;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknown0CostReplacements;
    }
}
