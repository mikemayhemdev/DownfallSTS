package downfall.actions;

import charbosses.bosses.AbstractCharBoss;
import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TriggerPseudoStartOfCombatAction extends AbstractGameAction {
    private AbstractCharBoss cB;

    public TriggerPseudoStartOfCombatAction(AbstractCharBoss cB) {
        this.cB = cB;
    }

    @Override
    public void update() {
        //Has a bunch of extra stuff in it
        //cB.usePreBattleAction();
        for (AbstractCharbossRelic r : cB.relics) {
            r.atBattleStartPreDraw();
        }
        for (AbstractCharbossRelic r : cB.relics) {
            r.atBattleStart();
        }
        isDone = true;
    }
}
