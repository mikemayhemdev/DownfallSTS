package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BagOfPreparation;

public class CBR_BagOfPreparation extends AbstractCharbossRelic {
    public static final String ID = "BagOfPreparation";

    public CBR_BagOfPreparation() {
        super(new BagOfPreparation());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }


    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        //this.addToBot(new EnemyDrawCardAction(this.owner, 2));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BagOfPreparation();
    }
    
}
