package theHexaghost.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theHexaghost.cards.AbstractHexaCard;

public class BurnVariable extends DynamicVariable {

    @Override
    public String key() {
        return "burny";
    } //TODO: Change this so your mod doesn't conflict!

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractHexaCard) card).isBurnModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractHexaCard) card).burn;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractHexaCard) {
            ((AbstractHexaCard) card).isBurnModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractHexaCard) card).baseBurn;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractHexaCard) card).upgradedBurn;
    }
}