package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;

public class EnemyGainEnergyAction extends AbstractGameAction
{
    private int energyGain;
    private AbstractCharBoss boss;

    public EnemyGainEnergyAction(final int amount) {
    	this(AbstractCharBoss.boss, amount);
    }
    public EnemyGainEnergyAction(final AbstractCharBoss target, final int amount) {
        this.setValues(target, target, 0);
        this.duration = Settings.ACTION_DUR_FAST;
        this.energyGain = amount;
        this.boss = boss;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
        	if (this.boss == null)
        		this.boss = AbstractCharBoss.boss;
        	if (this.boss == null) {
        		this.isDone = true;
        		return;
        	}
            this.boss.gainEnergy(this.energyGain);
            for (final AbstractCard c : this.boss.hand.group) {
                c.triggerOnGainEnergy(this.energyGain, true);
            }
        }
        this.tickDuration();
    }
}
