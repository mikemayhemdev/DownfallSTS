package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Pear;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_Pear extends AbstractCharbossRelic {

    public CBR_Pear() {
        super(new Pear());
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(10, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Pear();
    }
}