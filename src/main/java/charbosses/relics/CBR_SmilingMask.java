package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SmilingMask;

public class CBR_SmilingMask extends AbstractCharbossRelic {
    public static final String ID = "Smiling Mask";

    public CBR_SmilingMask() {
        super(new SmilingMask());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SmilingMask();
    }
}
