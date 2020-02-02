package sneckomod.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.cards.AbstractSneckoCard;

public class SneckoSilly extends DynamicVariable {

    @Override
    public String key() {
        return "qqq";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractSneckoCard) card).isSillyModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractSneckoCard) card).silly;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractSneckoCard) {
            ((AbstractSneckoCard) card).isSillyModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractSneckoCard) card).baseSilly;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractSneckoCard) card).upgradedSilly;
    }
}