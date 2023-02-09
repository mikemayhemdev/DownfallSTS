package expansioncontent.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.cards.AbstractDownfallCard;

public class SecondDownfallMagic extends DynamicVariable {
    public String key() {
        return "DM2";
    }

    public boolean isModified(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).isSecondDownfallModified;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractDownfallCard)
            ((AbstractDownfallCard) card).isSecondDownfallModified = v;
    }

    public int value(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).secondDownfall;
    }

    public int baseValue(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).baseSecondDownfall;
    }

    public boolean upgraded(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).isSecondDownfallUpgraded;
    }
}