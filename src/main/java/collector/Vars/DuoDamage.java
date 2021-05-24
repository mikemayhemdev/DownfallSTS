package collector.Vars;

import basemod.abstracts.DynamicVariable;
import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DuoDamage extends DynamicVariable {
    @Override
    public String key() {
        return "DUO:D";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).isDuoDamageModified;
        } else {
            return false;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).douDamage;
        } else {
            return 0;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).douBaseDamage;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).upgradedDuoDamage;
        } else {
            return false;
        }
    }
}