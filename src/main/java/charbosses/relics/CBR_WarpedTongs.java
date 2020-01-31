package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WarpedTongs;

import charbosses.actions.unique.EnemyUpgradeRandomCardAction;
import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;

public class CBR_WarpedTongs extends AbstractCharbossRelic
{
    
    public CBR_WarpedTongs() {
        super(new WarpedTongs());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        this.addToBot(new EnemyUpgradeRandomCardAction());
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_WarpedTongs();
    }
}
