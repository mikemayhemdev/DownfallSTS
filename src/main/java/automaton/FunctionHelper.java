package automaton;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cardmods.ExhaustCardMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.FunctionCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class FunctionHelper {
    public static ArrayList<AbstractCard> held;
    public static int max = 4;

    public static void init() {
        held = new ArrayList<>();
    }

    public static AbstractCard makeFunction() {
        AbstractCard q = new FunctionCard();
        CardModifierManager.addModifier(q, new ExhaustCardMod());
        for (AbstractCard c : held) {
            if (c instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) c).onCompile(q);
            } else {
                CardModifierManager.addModifier(q, new CardEffectsCardMod(c));
            }
        }
        System.out.println(q.rawDescription);
        return q;
    }

    //If everything is a cardmod, things can be done mostly dynamically.
    //Damages-card-mod, blocks-card-mod, etc.
    //The main issue would be being too dynamic.
    //"Apply 1 Vulnerable. NL Deal 10 damage." isn't any good.
    //The alternative is using similar tech to RepeatCardAction to make the FunctionCard silent-play held1, held2, held3, held4, utilizing their Compile bonuses correctly.
    //That's probably cleaner, and makes stuff like Vuln -> Damage clearer, if it has to happen.
    //Hmmm.
}
