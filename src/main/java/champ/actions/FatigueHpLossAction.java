package champ.actions;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FatigueHpLossAction extends LoseHPAction {

    public FatigueHpLossAction(AbstractCreature target, AbstractCreature source, int amount) {
        super(target, source, amount, AttackEffect.NONE);
        actionType = ActionType.SPECIAL;
    }

}
