package champ.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class ModifyDamageAndMagicAction extends AbstractGameAction {
    private final UUID uuid;

    public ModifyDamageAndMagicAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.superFlash();
            c.magicNumber += this.amount;
            c.baseMagicNumber += this.amount;
            c.damage += this.amount;
        }
        this.isDone = true;
    }
}
