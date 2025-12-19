package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SacredBark;

public class CBR_SacredBark extends AbstractCharbossRelic {
    public static final String ID = "SacredBark";

    public CBR_SacredBark() {
        super(new SacredBark());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SacredBark();
    }
}
