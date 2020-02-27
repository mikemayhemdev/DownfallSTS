package charbosses.relics;

import charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Lantern;

public class CBR_FrozenEgg extends AbstractCharbossRelic {

    private boolean firstTurn = true;

    public CBR_FrozenEgg() {
        super(new Lantern());
    }

    public void atPreBattle() {
        this.firstTurn = true;
    }

    public void atTurnStart() {
        if (this.firstTurn) {
            this.flash();
            this.addToTop(new EnemyGainEnergyAction(1));
            this.addToTop(new RelicAboveCreatureAction(this.owner, this));
            this.firstTurn = false;
        }

    }
    

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FrozenEgg();
    }
}
