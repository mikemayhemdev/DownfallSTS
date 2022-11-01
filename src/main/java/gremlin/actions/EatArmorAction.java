package gremlin.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EatArmorAction extends AbstractGameAction {
    private final AbstractCreature c;

    public EatArmorAction(AbstractCreature c) {
        this.c = c;
        this.actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        int amount = c.currentBlock;
        AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(c, c));
        AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(c, c, amount));
        this.isDone = true;
    }
}

