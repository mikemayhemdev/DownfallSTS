package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MockeryAction extends AbstractGameAction {
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
            if (this.target.getPower("Weakened").amount >= 3) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, source, amount));
            }
        }
        this.tickDuration();
    }
}
