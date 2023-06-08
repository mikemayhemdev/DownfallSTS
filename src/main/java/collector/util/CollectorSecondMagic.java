package collector.util;

import automaton.cards.AbstractBronzeCard;
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
        return ((AbstractCollectorCard) card).ismagic2Modified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractCollectorCard)card).magic2;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractCollectorCard) {
            ((AbstractCollectorCard) card).ismagic2Modified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractCollectorCard) card).basemagic2;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractCollectorCard) card).upgradedmagic2;
    }
}