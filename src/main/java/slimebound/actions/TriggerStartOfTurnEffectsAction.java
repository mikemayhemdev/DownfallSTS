package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;


public class TriggerStartOfTurnEffectsAction extends AbstractGameAction {

    private AbstractPlayer p;

    public TriggerStartOfTurnEffectsAction(AbstractPlayer p) {
        this.p = p;


    }

    public void update() {
        p.applyStartOfTurnPowers();
        p.applyStartOfTurnCards();
        p.applyStartOfTurnPostDrawRelics();
        p.applyStartOfTurnRelics();
        p.applyStartOfTurnPostDrawPowers();
        p.applyStartOfTurnOrbs();
        this.isDone = true;
    }
}

