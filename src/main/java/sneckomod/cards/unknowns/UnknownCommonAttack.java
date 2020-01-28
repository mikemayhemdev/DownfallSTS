package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownCommonAttack extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownCommonAttack");

    public UnknownCommonAttack() {
        super(ID, CardType.ATTACK, CardRarity.COMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }
}
