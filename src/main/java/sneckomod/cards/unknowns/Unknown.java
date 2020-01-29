package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class Unknown extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown");

    public Unknown() {
        super(ID, CardType.SKILL, CardRarity.BASIC);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> true;
    }
}
