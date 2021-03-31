package gremlin.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;

public class EatArmorAction extends AbstractGameAction
{
    private AbstractCreature c;

    public EatArmorAction(AbstractCreature c) {
        this.c = c;
        this.actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        int amount = c.currentBlock;
        AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(c,c));
        AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(c,c,amount));
        this.isDone = true;
    }
}

