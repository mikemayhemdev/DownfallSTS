package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Ginger;

public class CBR_Ginger extends AbstractCharbossRelic {
    public static final String ID = "Ginger";

    public CBR_Ginger() {
        super(new Ginger());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Ginger();
    }
}
