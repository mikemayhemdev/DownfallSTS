//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package automaton.actions;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;

public class ChosenAction extends AbstractGameAction {
    private int miscIncrease;
    private UUID uuid;

    public ChosenAction(UUID targetUUID, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.uuid.equals(this.uuid)) {
                c.misc += this.miscIncrease;
                c.applyPowers();
                // c.baseDamage = c.misc;
            }
        }

        for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseDamage = c.misc) {
            c = (AbstractCard)var1.next();
            c.misc += this.miscIncrease;
            c.applyPowers();
        }
        for (AbstractCard c2: FunctionHelper.held.group){
            c2.misc += this.miscIncrease;
            c2.applyPowers();
        }

        this.isDone = true;
    }
}
