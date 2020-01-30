package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class Unknown1Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown0Cost");

    public Unknown1Cost() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 1;
    }
}
