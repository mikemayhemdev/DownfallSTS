package awakenedOne.cards.cardvars;

import awakenedOne.cards.AbstractAwakenedCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static awakenedOne.AwakenedOneMod.makeID;

public class SecondDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAwakenedCard) {
            ((AbstractAwakenedCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAwakenedCard) {
            return ((AbstractAwakenedCard) card).upgradedSecondDamage;
        }
        return false;
    }
}