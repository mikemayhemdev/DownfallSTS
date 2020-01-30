package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownX extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownX");

    public UnknownX() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == -2;
    }
}
