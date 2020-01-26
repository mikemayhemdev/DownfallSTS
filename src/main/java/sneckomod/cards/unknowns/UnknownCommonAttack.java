package sneckomod.cards.unknowns;

public class UnknownCommonAttack extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownCommonAttack");

    public UnknownCommonAttack() {
        super(ID, CardType.ATTACK, CardRarity.COMMON);
    }
}
