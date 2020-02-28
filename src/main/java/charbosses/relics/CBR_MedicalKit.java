package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_MedicalKit extends AbstractCharbossRelic {
    public static final String ID = "MedicalKit";

    public CBR_MedicalKit() {
        super(new Strawberry());
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(7, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MedicalKit();
    }
}