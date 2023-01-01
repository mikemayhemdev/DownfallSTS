package awakenedOne.cards.cardvars;

import awakenedOne.cards.AbstractAwakenedCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static awakenedOne.AwakenedOneMod.makeID;

public class ThirdMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).isThirdMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).thirdMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAwakenedCard) {
            ((AbstractAwakenedCard) card).isThirdMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).baseThirdMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).upgradedThirdMagic;
        }
        return false;
    }
}