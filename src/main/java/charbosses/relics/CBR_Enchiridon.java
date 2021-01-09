package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Enchiridion;
import com.megacrit.cardcrawl.relics.Turnip;

public class CBR_Enchiridon extends AbstractCharbossRelic {
    public static final String ID = "Enchiridion";

    public CBR_Enchiridon() {
        super(new Enchiridion());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        //don't do parent
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Enchiridon();
    }
}
