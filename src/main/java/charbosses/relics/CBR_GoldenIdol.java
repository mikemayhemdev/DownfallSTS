package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.GoldenIdol;

public class CBR_GoldenIdol extends AbstractCharbossRelic {
    public static final String ID = "GoldenIdol";

    public CBR_GoldenIdol() {
        super(new GoldenIdol());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_GoldenIdol();
    }
}
