package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class UnknownCommonSkill extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownCommonSkill");

    public UnknownCommonSkill() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }
}
