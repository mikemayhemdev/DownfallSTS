package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.orbs.GremlinStandby;

import java.util.ArrayList;

public class RandomGremlinSwapAction extends AbstractGameAction {

    public RandomGremlinSwapAction(){
    }


    @Override
    public void update() {
        if(!this.isDone) {
            ArrayList<GremlinStandby> options = new ArrayList<>();
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof GremlinStandby) {
                    options.add((GremlinStandby) orb);
                }
            }
            if (options.size() > 0) {
                GremlinStandby target = options.get(AbstractDungeon.miscRng.random(0, options.size() - 1));
                AbstractDungeon.actionManager.addToTop(new GremlinSwapAction(target));
            }
        }
        this.isDone = true;
    }
}
