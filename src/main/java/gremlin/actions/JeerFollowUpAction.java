package gremlin.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class JeerFollowUpAction extends AbstractGameAction
{
    public JeerFollowUpAction(final AbstractCreature target, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null && this.target.isDead) {
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, amount));

        }
        this.tickDuration();
    }
}
