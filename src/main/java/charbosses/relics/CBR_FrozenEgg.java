package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;

public class CBR_FrozenEgg extends AbstractCharbossRelic {
    public static final String ID = "FrozenEgg";
    int numCards = 0;

    public CBR_FrozenEgg() {
        super(new FrozenEgg2());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onTrigger() {
        numCards ++;
        this.description = this.getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FrozenEgg();
    }
}
