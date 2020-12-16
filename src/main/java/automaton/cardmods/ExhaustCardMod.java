package automaton.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExhaustCardMod extends BronzeCardMod {

    public ExhaustCardMod() {
        this.priority = 99;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.equals("")) {
            return rawDescription + "Exhaust.";
        }
        return rawDescription + " NL Exhaust.";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExhaustCardMod();
    }
}
