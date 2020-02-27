package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BagOfMarbles;
import com.megacrit.cardcrawl.relics.BagOfPreparation;

public class CBR_BagOfPreparation extends AbstractCharbossRelic {

    public CBR_BagOfPreparation() {
        super(new BagOfPreparation());
    }


    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new DrawCardAction(this.owner, 2));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BagOfPreparation();
    }
    
}
