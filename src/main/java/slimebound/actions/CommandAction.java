package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.orbs.SpawnedSlime;

public class CommandAction extends AbstractGameAction {

    public void update() {
        isDone = true;
        AbstractOrb oldestOrb = null;
        if (AbstractDungeon.player.maxOrbs > 0) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof SpawnedSlime) {
                    oldestOrb = o;
                    break;
                }

            }
            if (oldestOrb != null)
                addToTop(new TrigggerSpecificSlimeAttackAction(oldestOrb));
        }
    }
}
