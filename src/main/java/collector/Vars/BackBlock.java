package collector.Vars;

import basemod.abstracts.DynamicVariable;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BackBlock extends DynamicVariable {
    @Override
    public String key() {
        return "collector:RearBlock";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).isRearBlockModified;
        } else {
            return false;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).RearBlock;
        } else {
            return 0;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).RearBaseBlock;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).upgradedRearBlock;
        } else {
            return false;
        }
    }
}