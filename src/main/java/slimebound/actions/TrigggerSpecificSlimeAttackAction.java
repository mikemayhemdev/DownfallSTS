package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.orbs.DarklingSlime;


public class TrigggerSpecificSlimeAttackAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractOrb o;

    public TrigggerSpecificSlimeAttackAction(AbstractOrb o) {
        this.o = o;


    }


    public void update() {


        o.onStartOfTurn();
        for (AbstractOrb otherOrb : AbstractDungeon.player.orbs){
            if (otherOrb instanceof DarklingSlime){
                if (otherOrb != this.o){
                    otherOrb.onStartOfTurn();
                }
            }
        }


        this.isDone = true;
    }

}



