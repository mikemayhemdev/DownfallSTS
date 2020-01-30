package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Orichalcum;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.badlogic.gdx.math.*;

public class CBR_Orichalcum extends AbstractCharbossRelic
{
    public static final String ID = "Orichalcum";
    private static final int BLOCK_AMT = 6;
    public boolean trigger;
    
    public CBR_Orichalcum() {
        super(new Orichalcum());
        this.trigger = false;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 6 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void onPlayerEndTurn() {
        if (AbstractCharBoss.boss.currentBlock == 0 || this.trigger) {
            this.trigger = false;
            this.flash();
            this.stopPulse();
            this.addToTop(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, 6));
            this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        }
    }
    
    @Override
    public void atTurnStart() {
        this.trigger = false;
        if (AbstractCharBoss.boss.currentBlock == 0) {
            this.beginLongPulse();
        }
    }
    
    @Override
    public int onPlayerGainedBlock(final float blockAmount) {
        if (blockAmount > 0.0f) {
            this.stopPulse();
        }
        return MathUtils.floor(blockAmount);
    }
    
    @Override
    public void onVictory() {
        this.stopPulse();
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Orichalcum();
    }
}
