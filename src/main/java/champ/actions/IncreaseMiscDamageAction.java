//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;

public class IncreaseMiscDamageAction extends AbstractGameAction {
    private int miscIncrease;
    private UUID uuid;

    public IncreaseMiscDamageAction(UUID targetUUID, int miscValue, int miscIncrease) {
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
                c.baseDamage = c.misc;
            }
        }

        for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseDamage = c.misc) {
            c = (AbstractCard)var1.next();
            c.misc += this.miscIncrease;
            c.applyPowers();
        }

        this.isDone = true;
    }
}
