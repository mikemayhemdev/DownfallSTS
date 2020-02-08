package guardian.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import guardian.cards.AbstractGuardianCard;

public class MultihitVariable extends DynamicVariable {
    @Override
    public String key() {
        return "GuardianMulti";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractGuardianCard) {
            AbstractGuardianCard asc = (AbstractGuardianCard) card;
            return asc.isMultihitModified;
        } else {
            return false;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractGuardianCard) {
            AbstractGuardianCard asc = (AbstractGuardianCard) card;
            return asc.multihit;
        } else {
            return 0;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractGuardianCard) {
            AbstractGuardianCard asc = (AbstractGuardianCard) card;
            return asc.multihit;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractGuardianCard) {
            AbstractGuardianCard asc = (AbstractGuardianCard) card;
            return asc.isMultihitModified;
        } else {
            return false;
        }
    }
}