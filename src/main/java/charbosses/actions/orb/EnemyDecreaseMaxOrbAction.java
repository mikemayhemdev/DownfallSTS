package charbosses.actions.orb;


import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class EnemyDecreaseMaxOrbAction extends AbstractGameAction {
    public EnemyDecreaseMaxOrbAction(int slotIncrease) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = slotIncrease;
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for(int i = 0; i < this.amount; ++i) {
                AbstractCharBoss.boss.decreaseMaxOrbSlots(1);
            }
        }

        this.tickDuration();
    }
}

