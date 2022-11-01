package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OddMushroom;

public class CBR_OddMushroom extends AbstractCharbossRelic {
    public static final String ID = "OddMushroom";

    public CBR_OddMushroom() {
        super(new OddMushroom());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OddMushroom();
    }


}
