package champ.util;

import basemod.abstracts.DynamicVariable;
import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CoolVariable extends DynamicVariable {

    @Override
    public String key() {
        return "cool";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractChampCard) card).isCoolModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractChampCard) card).cool;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractChampCard) {
            ((AbstractChampCard) card).isCoolModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractChampCard) card).baseCool;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractChampCard) card).upgradedCool;
    }
}