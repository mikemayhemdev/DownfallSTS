package charbosses.actions.orb;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class EnemyTriggerEndOfTurnOrbActions extends AbstractGameAction {
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
