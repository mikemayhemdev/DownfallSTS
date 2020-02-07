package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Vajra;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;

public class CBR_Vajra extends AbstractCharbossRelic
{
    public static final String ID = "Vajra";
    private static final int STR = 1;
    
    public CBR_Vajra() {
        super(new Vajra());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Vajra();
    }
}
