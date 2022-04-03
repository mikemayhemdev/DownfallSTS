package timeeater.cards.vars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import timeeater.TimeEaterMod;
import timeeater.cards.QuantumStrike;
import timeeater.suspend.SuspendHelper;

import static timeeater.util.Wiz.adp;

public class QuantumStrikeVar extends DynamicVariable {
    @Override
    public String key() {
        return TimeEaterMod.makeID("quantumStrike");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return value(card) != 0;
    }

    @Override
    public int value(AbstractCard card) {
        int count = 0;
        for (AbstractCard c : adp().drawPile.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : adp().hand.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : adp().discardPile.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : adp().exhaustPile.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : SuspendHelper.suspendGroup.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
