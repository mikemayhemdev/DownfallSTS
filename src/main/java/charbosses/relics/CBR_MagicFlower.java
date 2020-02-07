package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MagicFlower;
import com.megacrit.cardcrawl.rooms.*;
import com.badlogic.gdx.math.*;

public class CBR_MagicFlower extends AbstractCharbossRelic
{
    
    public CBR_MagicFlower() {
        super(new MagicFlower(), RelicTier.COMMON);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public int onPlayerHeal(final int healAmount) {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            return MathUtils.round(healAmount * 1.5f);
        }
        return healAmount;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MagicFlower();
    }
}
