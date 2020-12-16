package automaton.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ReducedCostMod extends BronzeCardMod {

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.modifyCostForCombat(-1);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.equals("")) {
            return "Costs 1 less.";
        }
        return rawDescription + " NL Costs 1 less.";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ReducedCostMod();
    }
}
