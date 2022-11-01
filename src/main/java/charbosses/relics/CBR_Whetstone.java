package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Whetstone;

public class CBR_Whetstone extends AbstractCharbossRelic {
    public static final String ID = "Whetstone";

    public CBR_Whetstone() {
        super(new Whetstone());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Whetstone();
    }
}
