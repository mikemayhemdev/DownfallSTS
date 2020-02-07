package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TungstenRod;

public class CBR_TungstenRod extends AbstractCharbossRelic
{
    
    public CBR_TungstenRod() {
        super(new TungstenRod());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public int onLoseHpLast(final int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            return damageAmount - 1;
        }
        return damageAmount;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_TungstenRod();
    }
}
