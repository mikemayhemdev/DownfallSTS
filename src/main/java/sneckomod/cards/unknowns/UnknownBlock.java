package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownBlock extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownX");

    public UnknownBlock() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.baseBlock > 0;
    }
}
