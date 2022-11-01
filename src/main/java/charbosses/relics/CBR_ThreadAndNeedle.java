package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ThreadAndNeedle;

public class CBR_ThreadAndNeedle extends AbstractCharbossRelic {
    public static final String ID = "ThreadAndNeedle";

    public CBR_ThreadAndNeedle() {
        super(new ThreadAndNeedle());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 4 + this.DESCRIPTIONS[1];
    }


    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, 4), 4));
        this.addToTop(new RelicAboveCreatureAction(this.owner, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ThreadAndNeedle();
    }
}
