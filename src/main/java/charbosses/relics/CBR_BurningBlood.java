package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BurningBlood;

public class CBR_BurningBlood extends AbstractCharbossRelic
{
    public static final String ID = "Burning Blood";
    private static final int HEALTH_AMT = 6;
    
    public CBR_BurningBlood() {
        super(new BurningBlood());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 6 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BurningBlood();
    }
}
