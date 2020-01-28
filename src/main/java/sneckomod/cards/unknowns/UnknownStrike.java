package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownStrike extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownStrike");

    public UnknownStrike() {
        super(ID, CardType.ATTACK, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.hasTag(CardTags.STRIKE);
    }
}
