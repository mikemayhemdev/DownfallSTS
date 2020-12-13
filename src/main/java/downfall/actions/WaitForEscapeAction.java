package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WaitForEscapeAction extends AbstractGameAction {
    public WaitForEscapeAction() {
        this.actionType = ActionType.WAIT;

        this.duration = this.startDuration = 15.0f; //Just in case, this can at most delay for 15 seconds.
    }

    @Override
    public void update() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.isEscaping && !m.escaped)
            {
                this.tickDuration();
                return;
            }
        }

        this.isDone = true;
    }
}
