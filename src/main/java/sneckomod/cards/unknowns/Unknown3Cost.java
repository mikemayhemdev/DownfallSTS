package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class Unknown3Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown3Cost");

    public Unknown3Cost() {
        super(ID, CardType.SKILL, CardRarity.RARE);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 3;
    }
}
