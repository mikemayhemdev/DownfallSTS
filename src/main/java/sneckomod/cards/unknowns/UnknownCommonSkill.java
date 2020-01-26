package sneckomod.cards.unknowns;

public class UnknownCommonSkill extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownCommonSkill");

    public UnknownCommonSkill() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }
}
