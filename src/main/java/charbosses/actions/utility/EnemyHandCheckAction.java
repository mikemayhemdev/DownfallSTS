package charbosses.actions.utility;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class EnemyHandCheckAction extends AbstractGameAction {
    private AbstractCharBoss player;

    public EnemyHandCheckAction() {
        this.player = AbstractCharBoss.boss;
    }

    @Override
    public void update() {
        this.player.hand.applyPowers();
        this.player.hand.glowCheck();
        this.isDone = true;
    }
}
