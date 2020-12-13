package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SsserpentHead;

public class CBR_MaskSerpent extends AbstractCharbossRelic {
    public static final String ID = "CBRSerpentHead";
    public CBR_MaskSerpent() {
        super(new SsserpentHead());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskSerpent();
    }
}
