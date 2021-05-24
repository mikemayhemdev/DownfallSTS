package collector.actions;

import collector.CollectorChar;
import collector.TorchChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class FaintAction extends AbstractGameAction {
    CollectorChar master;

    public FaintAction(CollectorChar master) {
        actionType = ActionType.SPECIAL;
        this.master = master;
    }

    public void update() {
        isDone = true;

        TorchChar d = master.torch;
        Iterator<AbstractPower> it = d.powers.iterator();
        while (it.hasNext()) {
            AbstractPower p = it.next();
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                p.onRemove();
                it.remove();
            }
        }

        master.setAggro(0);
        master.setFront(master);
        AbstractDungeon.onModifyPower();
    }
}