package charbosses.relics;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.orbs.EnemyLightning;
import charbosses.orbs.EnemyPlasma;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CrackedCore;
import com.megacrit.cardcrawl.relics.NuclearBattery;

public class CBR_NuclearBattery extends AbstractCharbossRelic {
    public static final String ID = "NuclearBattery";
    public CBR_NuclearBattery() {
        super(new NuclearBattery());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new EnemyChannelAction(new EnemyPlasma()));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_NuclearBattery();
    }

}