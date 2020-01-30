package charbosses.actions.orb;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;

import charbosses.bosses.AbstractCharBoss;

import java.util.*;

public class EnemyChannelAction extends AbstractGameAction
{
    private AbstractOrb orbType;
    private boolean autoEvoke;
    
    public EnemyChannelAction(final AbstractOrb newOrbType) {
        this(newOrbType, true);
    }
    
    public EnemyChannelAction(final AbstractOrb newOrbType, final boolean autoEvoke) {
        this.autoEvoke = false;
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.autoEvoke) {
                AbstractCharBoss.boss.channelOrb(this.orbType);
            }
            else {
                for (final AbstractOrb o : AbstractCharBoss.boss.orbs) {
                    if (o instanceof EmptyOrbSlot) {
                    	AbstractCharBoss.boss.channelOrb(this.orbType);
                        break;
                    }
                }
            }
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
