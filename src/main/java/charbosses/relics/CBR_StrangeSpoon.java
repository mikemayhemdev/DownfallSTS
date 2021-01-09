package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StrangeSpoon;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_StrangeSpoon extends AbstractCharbossRelic {
    public static final String ID = "StrangeSpoon";

    public CBR_StrangeSpoon() {
        super(new StrangeSpoon());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_StrangeSpoon();
    }
}