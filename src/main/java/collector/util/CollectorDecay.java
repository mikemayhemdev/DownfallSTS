package collector.util;

import basemod.abstracts.DynamicVariable;
import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CollectorDecay extends DynamicVariable {

    @Override
    public String key() {
        return "clDecay";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractCollectorCard)card).combatChargesRemaining;
    }

    public void setIsModified(AbstractCard card, boolean v) {

    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractCollectorCard) card).baseCombatCharges;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}