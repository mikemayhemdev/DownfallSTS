package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WarPaint;

public class CBR_WarPaint extends AbstractCharbossRelic {
    public static final String ID = "WarPaint";

    public CBR_WarPaint() {
        super(new WarPaint());
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
        return new CBR_WarPaint();
    }
}
