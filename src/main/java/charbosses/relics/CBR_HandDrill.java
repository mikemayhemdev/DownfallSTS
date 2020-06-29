package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.HandDrill;

public class CBR_HandDrill extends AbstractCharbossRelic {
    public static final String ID = "HandDrill";

    public CBR_HandDrill() {
        super(new HandDrill());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_HandDrill();
    }

    @Override
    public void onBlockBroken(final AbstractCreature m) {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new ApplyPowerAction(m, this.owner, new VulnerablePower(m, 2, true), 2));
    }
}
