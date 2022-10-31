package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Iterator;

public class RemoveRandomDebuffAction extends AbstractGameAction {
    private AbstractCreature c;

    public RemoveRandomDebuffAction(AbstractCreature c) {
        this.c = c;
        this.duration = 0.5F;
    }

    public void update() {
        Iterator var1 = this.c.powers.iterator();
        ArrayList<AbstractPower> debuffList = new ArrayList<>();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                debuffList.add(p);
            }
        }

        if (!debuffList.isEmpty()) {
            int index = AbstractDungeon.cardRng.random(0, debuffList.size() - 1);
            this.addToTop(new RemoveSpecificPowerAction(this.c, this.c, debuffList.get(index).ID));
        }
        this.isDone = true;
    }
}

