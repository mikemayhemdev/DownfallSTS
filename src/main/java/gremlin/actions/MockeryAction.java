package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;

public class MockeryAction extends AbstractGameAction
{
    public MockeryAction(final AbstractCreature target, AbstractCreature source, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.source = source;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null && this.target.hasPower("Weakened")) {
            if(this.target.getPower("Weakened").amount >= 3) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, source, amount));
            }
        }
        this.tickDuration();
    }
}
