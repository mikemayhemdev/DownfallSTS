package awakenedOne.cards.cardvars;

import awakenedOne.cards.AbstractAwakenedCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static awakenedOne.AwakenedOneMod.makeID;

public class SecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).secondMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAwakenedCard) {
            ((AbstractAwakenedCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).upgradedSecondMagic;
        }
        return false;
    }
}