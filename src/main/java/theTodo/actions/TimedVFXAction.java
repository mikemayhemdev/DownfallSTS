package theTodo.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TimedVFXAction extends AbstractGameAction {
    private AbstractGameEffect effect;
    private boolean added = false;

    public TimedVFXAction(AbstractGameEffect effect) {
        this.effect = effect;
    }

    @Override
    public void update() {
        if (!added) {
            AbstractDungeon.effectList.add(effect);
            added = true;
        }
        isDone = effect.isDone;
    }
}