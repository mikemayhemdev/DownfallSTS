package charbosses.relics;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.orbs.EnemyLightning;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CrackedCore;

public class CBR_CrackedCore extends AbstractCharbossRelic {
    public CBR_CrackedCore() {
        super(new CrackedCore());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new EnemyChannelAction(new EnemyLightning()));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CrackedCore();
    }

}