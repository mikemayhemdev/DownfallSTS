package charbosses.actions.common;


import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.EnNeutralStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class EnemyNotStanceCheckAction extends AbstractGameAction {
    private final AbstractGameAction actionToBuffer;

    public EnemyNotStanceCheckAction(AbstractGameAction actionToCheck) {
        this.actionToBuffer = actionToCheck;
    }

    public void update() {
        //SlimeboundMod.logger.info(AbstractCharBoss.boss.stance);
        //SlimeboundMod.logger.info(AbstractCharBoss.boss.stance instanceof EnNeutralStance);
        if (!(AbstractCharBoss.boss.stance instanceof EnNeutralStance)) {
            this.addToBot(this.actionToBuffer);
        }

        this.isDone = true;
    }
}
