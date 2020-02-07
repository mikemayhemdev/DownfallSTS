package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.rooms.*;

import charbosses.bosses.AbstractCharBoss;

public class CBR_Anchor extends AbstractCharbossRelic
{
    
    public CBR_Anchor() {
        super(new Anchor());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 10 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        this.addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, 10));
        this.grayscale = true;
    }
    
    @Override
    public void justEnteredRoom(final AbstractRoom room) {
        this.grayscale = false;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Anchor();
    }
}
