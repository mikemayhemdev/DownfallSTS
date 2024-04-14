package hermit.actions;

import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class ObtainPotionNotBlockedByCombatEndAction extends ObtainPotionAction {
    public ObtainPotionNotBlockedByCombatEndAction(AbstractPotion potion) {
        super(potion);
        this.actionType = ActionType.DAMAGE;
    }
}
