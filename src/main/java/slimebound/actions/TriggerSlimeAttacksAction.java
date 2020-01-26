package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.orbs.SpawnedSlime;


public class TriggerSlimeAttacksAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractPlayer p;

    public TriggerSlimeAttacksAction(AbstractPlayer p) {
        this.p = p;


    }


    public void update() {


        for (AbstractOrb o : p.orbs) {

            if (o instanceof SpawnedSlime) {
                o.onStartOfTurn();

            }
        }


        this.isDone = true;
    }

}



