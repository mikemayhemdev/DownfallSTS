package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownColorless extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownColorless");

    public UnknownColorless() {
        super(ID, CardType.SKILL, CardRarity.RARE);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == CardColor.COLORLESS;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownColorlessReplacements;
    }
}
