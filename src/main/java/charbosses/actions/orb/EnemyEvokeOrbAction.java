package charbosses.actions.orb;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

public class EnemyEvokeOrbAction extends AbstractGameAction
{
    private int orbCount;
    
    public EnemyEvokeOrbAction(final int amount) {
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        }
        else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
        this.duration = this.startDuration;
        this.orbCount = amount;
        this.actionType = ActionType.DAMAGE;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            for (int i = 0; i < this.orbCount; ++i) {
                AbstractCharBoss.boss.evokeOrb();
            }
        }
        this.tickDuration();
    }
}
