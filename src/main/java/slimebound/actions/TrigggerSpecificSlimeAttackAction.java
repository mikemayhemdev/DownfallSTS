package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class TrigggerSpecificSlimeAttackAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractOrb o;

    public TrigggerSpecificSlimeAttackAction(AbstractOrb o) {
        this.o = o;


    }


    public void update() {


        o.onStartOfTurn();


        this.isDone = true;
    }

}



