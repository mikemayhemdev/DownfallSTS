package sneckomod.cards.unknowns;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownRareAttack extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownRareAttack");

    public UnknownRareAttack() {
        super(ID, CardType.ATTACK, CardRarity.RARE);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownRareAttackReplacements;
    }
}
