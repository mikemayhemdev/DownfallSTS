package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NlothsMask;

public class CBR_MaskNloth extends AbstractCharbossRelic {
    public static final String ID = "CBRNlothsMask";
    public CBR_MaskNloth() {
        super(new NlothsMask());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskNloth();
    }
}
