package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.EmptyCage;

public class CBR_EmptyCage extends AbstractCharbossRelic {
    public static final String ID = "EmptyCage";

    public CBR_EmptyCage() {
        super(new EmptyCage());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_EmptyCage();
    }
}
