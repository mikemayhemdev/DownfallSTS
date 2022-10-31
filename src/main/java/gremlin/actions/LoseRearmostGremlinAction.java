package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;

import java.util.Collections;

public class LoseRearmostGremlinAction extends AbstractGameAction {
    public LoseRearmostGremlinAction(){}

    @Override
    public void update() {
        if (!(AbstractDungeon.player instanceof GremlinCharacter)) {
            this.isDone = true;
            return;
        }
        int position = -1;
        for(int i=0;i<AbstractDungeon.player.maxOrbs;i++){
            if(AbstractDungeon.player.orbs.get(i) instanceof GremlinStandby){
                position = i;
            }
        }
        if(position < 0){
            this.isDone = true;
            return;
        }

        GremlinStandby grem = (GremlinStandby) AbstractDungeon.player.orbs.get(position);
        ((GremlinCharacter) AbstractDungeon.player).gremlinDeathSFX(grem.assetFolder);
        ((GremlinCharacter) AbstractDungeon.player).enslave(grem.assetFolder);

        final AbstractOrb orbSlot = new EmptyOrbSlot();
        for (int i = position + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
            Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
        }
        AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
        }

        this.isDone = true;
    }
}
