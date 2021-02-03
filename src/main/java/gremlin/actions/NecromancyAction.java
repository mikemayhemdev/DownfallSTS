package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.GremlinMod;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;

import java.util.ArrayList;

public class NecromancyAction extends AbstractGameAction
{
    public NecromancyAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && AbstractDungeon.player instanceof GremlinCharacter) {
            ArrayList<String> grems = GremlinMod.getGremlinStrings();
            grems.remove(((GremlinCharacter) AbstractDungeon.player).currentGremlin);
            for(AbstractOrb orb: AbstractDungeon.player.orbs){
                if(orb instanceof GremlinStandby){
                    grems.remove(((GremlinStandby) orb).assetFolder);
                }
            }
            if(AbstractDungeon.player instanceof GremlinCharacter){
                grems.removeIf(grem -> ((GremlinCharacter) AbstractDungeon.player).mobState.isEnslaved(grem));
            }
            if(grems.size() > 0){
                String zombie = grems.get(AbstractDungeon.miscRng.random(0, grems.size() - 1));
                AbstractDungeon.actionManager.addToTop(new ChannelAction(
                        GremlinMod.getGremlinOrb(zombie, amount)
                ));
            }
        }
        this.tickDuration();
    }
}
