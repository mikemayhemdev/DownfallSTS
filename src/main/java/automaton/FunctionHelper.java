package automaton;

import automaton.cards.FunctionCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;

import java.util.ArrayList;

public class FunctionHelper {
    public static ArrayList<AbstractCard> held;

    public AbstractCard makeFunction() {
        return new FunctionCard(); //TODO: Make this do the thing
    }

    //If everything is a cardmod, things can be done mostly dynamically.
    //Damages-card-mod, blocks-card-mod, etc.
    //The main issue would be being too dynamic.
    //"Apply 1 Vulnerable. NL Deal 10 damage." isn't any good.
    //The alternative is using similar tech to RepeatCardAction to make the FunctionCard silent-play held1, held2, held3, held4, utilizing their Compile bonuses correctly.
    //That's probably cleaner, and makes stuff like Vuln -> Damage clearer, if it has to happen.
    //Hmmm.
}
