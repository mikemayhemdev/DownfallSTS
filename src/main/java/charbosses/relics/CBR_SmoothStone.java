package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;

public class CBR_SmoothStone extends AbstractCharbossRelic
{
    public static final String ID = "Oddly Smooth Stone";
    private static final int CON_AMT = 1;
    
    public CBR_SmoothStone() {
        super(new OddlySmoothStone());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new DexterityPower(AbstractCharBoss.boss, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SmoothStone();
    }
}
