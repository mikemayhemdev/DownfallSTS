package sneckomod.cards.unknowns;

public class UnknownRareSkill extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownRareSkill");

    public UnknownRareSkill() {
        super(ID, CardType.SKILL, CardRarity.RARE);
    }
}
