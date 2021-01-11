package champ.actions;

import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class ModifyDamageAndMagicAction extends AbstractGameAction {
    private UUID uuid;

    public ModifyDamageAndMagicAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.superFlash();
            ((AbstractChampCard)c).upp();
        }
        this.isDone = true;
    }
}
