package expansioncontent.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.cards.AbstractDownfallCard;

public class TagMagic extends DynamicVariable {
    public String key() {
        return "TM";
    }

    public boolean isModified(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).isTagMagicModified;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractDownfallCard)
            ((AbstractDownfallCard) card).isTagMagicModified = v;
    }

    public int value(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).tagMagic;
    }

    public int baseValue(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).baseTagMagic;
    }

    public boolean upgraded(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).upgradedTagMagic;
    }
}
