package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DataDisk;

public class CBR_DataDisk extends AbstractCharbossRelic {
    public static final String ID = "DataDisk";

    public CBR_DataDisk() {
        super(new DataDisk());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(this.owner, this));
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DataDisk();
    }

}