package charbosses.actions.orb;

import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.cardpowers.EnemyMetallicizePower;
import charbosses.powers.cardpowers.EnemyTheBombPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

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

        for (final AbstractRelic r : AbstractCharBoss.boss.relics) {
            r.onPlayerEndTurn();
            r.onEnergyRecharge();
        }

        for (final AbstractPower p : AbstractCharBoss.boss.powers) {
            if (!AbstractCharBoss.boss.isPlayer) {
                p.atEndOfTurnPreEndTurnCards(false);
            }
            p.atEndOfTurn(AbstractCharBoss.boss.isPlayer);
           // p.onEnergyRecharge();
        }

        this.isDone = true;
    }
}
