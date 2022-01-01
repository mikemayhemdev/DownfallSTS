package collector.Vars;

import basemod.abstracts.DynamicVariable;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BackDamage extends DynamicVariable {
    @Override
    public String key() {
        return "collector:RearDamage";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).isRearDamageModified;
        } else {
            return false;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).RearDamage;
        } else {
            return 0;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).RearBaseDamage;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).upgradedRearDamage;
        } else {
            return false;
        }
    }
}