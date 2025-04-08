package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Orrery;
import com.megacrit.cardcrawl.relics.Toolbox;

public class CBR_Toolbox extends AbstractCharbossRelic {
    public static final String ID = "Toolbox";

    public CBR_Toolbox() {
        super(new Toolbox());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Toolbox();
    }
}