package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_SneckoSkull extends AbstractCharbossRelic {
    public static final String ID = "Snake Skull";

    public CBR_SneckoSkull() {
        super(new Strawberry());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SneckoSkull();
    }
}