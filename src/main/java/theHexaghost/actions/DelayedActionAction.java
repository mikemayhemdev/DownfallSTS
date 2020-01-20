package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DelayedActionAction extends AbstractGameAction {
    private AbstractGameAction a;

    public DelayedActionAction(AbstractGameAction b) {
        a = b;
    }

    public void update() {
        isDone = true;
        AbstractDungeon.actionManager.addToBottom(a);
    }
}
