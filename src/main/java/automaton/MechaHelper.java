package automaton;

import com.megacrit.cardcrawl.cards.CardGroup;

public class MechaHelper {
    public static CardGroup blasters;
    public static CardGroup shields;
    public static CardGroup cores;

    public static void init() {
        blasters = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        shields = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }


}
