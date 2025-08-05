package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.util.Wiz.atb;

public class ForTheHexAction extends AbstractGameAction {
    private AbstractMonster m;

    public ForTheHexAction(int weakAmt, AbstractMonster m) {
        this.actionType = ActionType.WAIT;
        this.amount = weakAmt;
        this.m = m;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            atb(new ConjureAction(false));
        }
        this.isDone = true;
    }
}