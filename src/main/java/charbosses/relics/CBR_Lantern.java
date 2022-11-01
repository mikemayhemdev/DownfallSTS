package charbosses.relics;

import charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Lantern;

public class CBR_Lantern extends AbstractCharbossRelic {
    public static final String ID = "Lantern";


    public CBR_Lantern() {
        super(new Lantern());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1] + LocalizedStrings.PERIOD;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new EnemyGainEnergyAction(1));
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Lantern();
    }
}
