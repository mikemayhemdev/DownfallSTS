package automaton.util;

import automaton.cards.AbstractBronzeCard;
import basemod.abstracts.DynamicVariable;
import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AutoVar extends DynamicVariable {

    @Override
    public String key() {
        return "bauto";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractBronzeCard) card).isAutoModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractBronzeCard) card).auto;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) card).isAutoModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractBronzeCard) card).baseAuto;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractBronzeCard) card).upgradedAuto;
    }
}