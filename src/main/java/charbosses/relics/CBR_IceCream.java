package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IceCream;

public class CBR_IceCream extends AbstractCharbossRelic {
    public CBR_IceCream() {
        super(new IceCream());
        this.tier = RelicTier.UNCOMMON;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_IceCream();
    }
}
