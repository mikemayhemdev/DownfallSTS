package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.Turnip;

public class CBR_PaperKrane extends AbstractCharbossRelic {
    public static final String ID = "Paper Crane";

    public CBR_PaperKrane() {
        super(new PaperCrane());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PaperKrane();
    }
}
