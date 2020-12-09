package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Strawberry;
import com.megacrit.cardcrawl.relics.Waffle;

public class CBR_LeesWaffle extends AbstractCharbossRelic {
    public static final String ID = "LeesWaffle";

    public CBR_LeesWaffle() {
        super(new Waffle());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 7 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(7, true);
        this.owner.heal(this.owner.maxHealth);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_LeesWaffle();
    }
}