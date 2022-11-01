package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;

public class CBR_Necronomicon extends AbstractCharbossRelic {
    public static final String ID = "Necronomicon";

    public CBR_Necronomicon() {
        super(new Necronomicon());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Necronomicon();
    }
}
