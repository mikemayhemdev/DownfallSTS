package sneckomod.cards.unknowns;

public class UnknownRareAttack extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownRareAttack");

    public UnknownRareAttack() {
        super(ID, CardType.ATTACK, CardRarity.RARE);
    }
}
