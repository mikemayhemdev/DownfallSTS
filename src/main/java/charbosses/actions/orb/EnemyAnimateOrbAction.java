package charbosses.actions.orb;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

public class EnemyAnimateOrbAction extends AbstractGameAction
{
    private int orbCount;
    
    public EnemyAnimateOrbAction(final int amount) {
        this.orbCount = amount;
    }
    
    @Override
    public void update() {
        for (int i = 0; i < this.orbCount; ++i) {
            AbstractCharBoss.boss.triggerEvokeAnimation(i);
        }
        this.isDone = true;
    }
}
