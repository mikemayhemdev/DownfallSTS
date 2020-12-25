package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Orrery;

public class CBR_Orrery extends AbstractCharbossRelic {
    public static final String ID = "Orrery";

    public CBR_Orrery() {
        super(new Orrery());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Orrery();
    }
}
