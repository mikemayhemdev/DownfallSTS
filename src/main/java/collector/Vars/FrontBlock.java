package collector.Vars;

import basemod.abstracts.DynamicVariable;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FrontBlock extends DynamicVariable {
    @Override
    public String key() {
        return "collector:FrontBlock";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).isFrontBlockModified;
        } else {
            return false;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).FrontBlock;
        } else {
            return 0;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).FrontBaseBlock;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractCollectorCard) {
            return ((AbstractCollectorCard) card).upgradedFrontBlock;
        } else {
            return false;
        }
    }
}