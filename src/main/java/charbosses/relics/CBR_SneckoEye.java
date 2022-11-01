package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SneckoEye;

public class CBR_SneckoEye extends AbstractCharbossRelic {
    public static final String ID = "SneckoEye";

    public CBR_SneckoEye() {
        super(new SneckoEye());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.owner.masterHandSize += 1;
    }

    @Override
    public void onUnequip() {
        this.owner.masterHandSize -= 1;
    }


    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ConfusionPower(this.owner)));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SneckoEye();
    }
}
