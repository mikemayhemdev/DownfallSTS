package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;

public class CBR_Circlet extends AbstractCharbossRelic {

    public CBR_Circlet() {
        super(new Circlet());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Circlet();
    }
}
