package charbosses.actions.orb;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;

import charbosses.bosses.AbstractCharBoss;

import java.util.*;

public class EnemyTriggerEndOfTurnOrbActions extends AbstractGameAction
{
    @Override
    public void update() {
        if (!AbstractCharBoss.boss.orbs.isEmpty()) {
            for (final AbstractOrb o : AbstractCharBoss.boss.orbs) {
                o.onEndOfTurn();
            }
            if (AbstractCharBoss.boss.hasRelic("Cables") && !(AbstractCharBoss.boss.orbs.get(0) instanceof EmptyOrbSlot)) {
            	AbstractCharBoss.boss.orbs.get(0).onEndOfTurn();
            }
        }
        this.isDone = true;
    }
}
