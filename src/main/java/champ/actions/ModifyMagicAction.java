package champ.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class ModifyMagicAction extends AbstractGameAction {
    private UUID uuid;

    public ModifyMagicAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.superFlash();
            c.magicNumber += this.amount;
            c.baseMagicNumber += this.amount;
        }
        this.isDone = true;
    }
}
