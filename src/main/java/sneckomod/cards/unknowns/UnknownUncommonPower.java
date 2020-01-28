package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownUncommonPower extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownUncommonPower");

    public UnknownUncommonPower() {
        super(ID, CardType.POWER, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }
}
