package collector.util;

import basemod.abstracts.DynamicVariable;
import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CollectorSecondMagic extends DynamicVariable {

    @Override
    public String key() {
        return "clm2";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractCollectorCard) card).issecondMagicModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractCollectorCard)card).secondMagic;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractCollectorCard) {
            ((AbstractCollectorCard) card).issecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractCollectorCard) card).baseSecondMagic;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractCollectorCard) card).upgradedsecondMagic;
    }
}