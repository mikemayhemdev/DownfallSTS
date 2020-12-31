package charbosses.actions.common;


import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.AbstractEnemyStance;
import charbosses.stances.EnCalmStance;
import charbosses.stances.EnNeutralStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;

public class EnemyNotStanceCheckAction extends AbstractGameAction {
    private AbstractGameAction actionToBuffer;

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
