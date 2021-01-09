package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Ginger;
import com.megacrit.cardcrawl.relics.Turnip;

public class CBR_Turnip extends AbstractCharbossRelic {
    public static final String ID = "Turnip";

    public CBR_Turnip() {
        super(new Turnip());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Turnip();
    }
}
