package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import gremlin.GremlinMod;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;
import gremlin.powers.GremlinNobPower;

import java.util.Collections;
import java.util.Random;

public class GremlinSwapAction extends AbstractGameAction {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Gremlin:Swap");

    private GremlinStandby gremlin;
    private boolean fromRelic = false;

    public GremlinSwapAction() {
        this.gremlin = null;
    }

    public GremlinSwapAction(boolean fromRelic) {
        this.gremlin = null;
        this.fromRelic = fromRelic;
    }

    public GremlinSwapAction(GremlinStandby gremlin) {
        this.gremlin = gremlin;
    }

    @Override
    public void update() {
        if (!(AbstractDungeon.player instanceof GremlinCharacter)) {
            this.isDone = true;
            return;
        }
        if(AbstractDungeon.player.hasPower(GremlinNobPower.POWER_ID)){
            if(!fromRelic) {
                Random r = new Random();
                int roll = r.nextInt(3);
                if (roll == 0) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1A"));
                } else if (roll == 1) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1B"));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1C"));
                }
                String talk = uiStrings.TEXT[roll];
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, talk, 1.0F, 2.0F));
            }
            this.isDone = true;
            return;
        }
        int position = -1;
        if(this.gremlin == null){
            for(int i=0;i<AbstractDungeon.player.maxOrbs;i++){
                if(AbstractDungeon.player.orbs.get(i) instanceof GremlinStandby){
                    position = i;
                    this.gremlin = (GremlinStandby) AbstractDungeon.player.orbs.get(i);
                    break;
                }
            }
        }
        else{
            for(int i=0;i<AbstractDungeon.player.maxOrbs;i++){
                if(AbstractDungeon.player.orbs.get(i) instanceof GremlinStandby){
                    if(((GremlinStandby) AbstractDungeon.player.orbs.get(i)).assetFolder.equals(this.gremlin.assetFolder)) {
                        position = i;
                        this.gremlin = (GremlinStandby) AbstractDungeon.player.orbs.get(i);
                        break;
                    }
                }
            }
        }
        if(position < 0){
            this.isDone = true;
            return;
        }

        GremlinStandby gremlinOrb = GremlinMod.getGremlinOrb(
                ((GremlinCharacter) (AbstractDungeon.player)).swapBody(gremlin.assetFolder, gremlin.animationName));
        AbstractDungeon.player.currentHealth = gremlin.hp;
        AbstractDungeon.player.healthBarUpdatedEvent();

        final AbstractOrb orbSlot = new EmptyOrbSlot();
        for (int i = position + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
            Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
        }
        AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
        }

        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, gremlin.getPower(), 1));

        if(gremlinOrb.hp > 0) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(gremlinOrb));
        } else {
            GremlinMod.onGremlinDeath();
        }

        GremlinMod.onSwap();

        this.isDone = true;
    }
}
