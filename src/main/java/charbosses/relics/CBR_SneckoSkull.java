package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SneckoSkull;

public class CBR_SneckoSkull extends AbstractCharbossRelic {
    public static final String ID = "Snake Skull";

    public CBR_SneckoSkull() {
        super(new SneckoSkull());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SneckoSkull();
    }
}