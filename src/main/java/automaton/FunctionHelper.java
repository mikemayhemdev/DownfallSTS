package automaton;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;

public class FunctionHelper {
    public static AbstractCard held1;
    public static AbstractCard held2;
    public static AbstractCard held3;
    public static AbstractCard held4;

    public AbstractCard makeFunction() {
        return new Shiv(); //TODO: Make this do the thing
    }
}
