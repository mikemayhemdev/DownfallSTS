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

    //If everything is a cardmod, things can be done mostly dynamically.
    //Damages-card-mod, blocks-card-mod, etc.
    //The main issue would be being too dynamic.
    //"Apply 1 Vulnerable. NL Deal 10 damage." isn't any good.
    //The alternative is using similar tech to RepeatCardAction to make the FunctionCard silent-play held1, held2, held3, held4, utilizing their Compile bonuses correctly.
    //That's probably cleaner, and makes stuff like Vuln -> Damage clearer, if it has to happen.
    //Hmmm.
}
