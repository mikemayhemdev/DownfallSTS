package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.relics.IceCream;

public class CBR_ChemicalX extends AbstractCharbossRelic {
    public static final String ID = "Chemical X";
    public CBR_ChemicalX() {
        super(new ChemicalX());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ChemicalX();
    }
}
