package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownInnate extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownInnate");

    public UnknownInnate() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.isInnate;
    }
}
