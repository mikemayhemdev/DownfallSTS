package charbosses.relics;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Mango;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_Strawberry extends AbstractCharbossRelic {

    public CBR_Strawberry() {
        super(new Strawberry());
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(7, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Strawberry();
    }
}