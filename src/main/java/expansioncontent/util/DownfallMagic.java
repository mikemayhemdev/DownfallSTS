package expansioncontent.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.cards.AbstractDownfallCard;

public class DownfallMagic extends DynamicVariable {
    public String key() {
        return "DM";
    }

    public boolean isModified(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).isDownfallModified;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractDownfallCard)
            ((AbstractDownfallCard) card).isDownfallModified = v;
    }

    public int value(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).downfallMagic;
    }

    public int baseValue(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).baseDownfallMagic;
    }

    public boolean upgraded(AbstractCard abstractCard) {
        return ((AbstractDownfallCard) abstractCard).isDownfallUpgraded;
    }
}
