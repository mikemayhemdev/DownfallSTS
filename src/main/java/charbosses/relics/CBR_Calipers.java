package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Calipers;

public class CBR_Calipers extends AbstractCharbossRelic {
    public static final String ID = "Calipers";

    public CBR_Calipers() {
        super(new Calipers());
        this.tier = RelicTier.BOSS;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 15 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Calipers();
    }

}
