package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SpiritPoop;

public class CBR_Abacus extends AbstractCharbossRelic {
    public CBR_Abacus() {
        super(new SpiritPoop());
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Abacus();
    }
}
