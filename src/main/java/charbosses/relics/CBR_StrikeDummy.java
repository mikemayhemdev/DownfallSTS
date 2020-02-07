package charbosses.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StrikeDummy;

public class CBR_StrikeDummy extends AbstractCharbossRelic
{
    public static final String ID = "StrikeDummy";
    
    public CBR_StrikeDummy() {
        super(new StrikeDummy());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public float atDamageModify(final float damage, final AbstractCard c) {
        if (c.hasTag(AbstractCard.CardTags.STRIKE)) {
            return damage + 3.0f;
        }
        return damage;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_StrikeDummy();
    }
}
