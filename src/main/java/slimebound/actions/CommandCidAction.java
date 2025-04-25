package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.relics.SlimedTailRelic;

public class CommandCidAction extends AbstractGameAction {

    public void update() {
        isDone = true;
        //TODO action
        //TODO minion master enchantment

        if (AbstractDungeon.player.hasRelic(SlimedTailRelic.ID)){
            AbstractDungeon.player.getRelic(SlimedTailRelic.ID).onTrigger();
        }
    }
}

