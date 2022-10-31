package collector.Vars;

import basemod.abstracts.DynamicVariable;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FrontDamage extends DynamicVariable {
    @Override
    public String key() {
        return "collector:FrontDamage";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).isFrontDamageModified;
        } else {
            return false;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).FrontDamage;
        } else {
            return 0;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).FrontBaseDamage;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).upgradedFrontDamage;
        } else {
            return false;
        }
    }
}
