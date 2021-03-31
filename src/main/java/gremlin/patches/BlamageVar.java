package gremlin.patches;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gremlin.cards.AbstractGremlinCard;

public class BlamageVar extends DynamicVariable {

    @Override
    public String key() {
        return "blamage";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractGremlinCard) card).isBlamageModifed;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractGremlinCard) card).blamage;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractGremlinCard) {
            ((AbstractGremlinCard) card).isBlamageModifed = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractGremlinCard) card).baseBlamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractGremlinCard) card).upgradedBlamage;
    }
}
