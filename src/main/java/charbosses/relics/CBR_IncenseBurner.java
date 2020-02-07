package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IncenseBurner;

import charbosses.bosses.AbstractCharBoss;

public class CBR_IncenseBurner extends AbstractCharbossRelic
{
    
    public CBR_IncenseBurner() {
        super(new IncenseBurner());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onEquip() {
        this.counter = 0;
    }
    
    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        }
        else {
            ++this.counter;
        }
        if (this.counter == 6) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.addToBot(new ApplyPowerAction(AbstractCharBoss.boss, null, new IntangiblePlayerPower(AbstractCharBoss.boss, 1), 1));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_IncenseBurner();
    }
}
