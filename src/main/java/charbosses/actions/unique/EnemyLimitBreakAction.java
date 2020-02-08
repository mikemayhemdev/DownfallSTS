package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EnemyLimitBreakAction extends AbstractGameAction {
    private AbstractCharBoss p;

    public EnemyLimitBreakAction() {
        this.actionType = ActionType.WAIT;
        this.p = AbstractCharBoss.boss;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower("Strength")) {
            final int strAmt = this.p.getPower("Strength").amount;
            this.addToTop(new ApplyPowerAction(this.p, this.p, new StrengthPower(this.p, strAmt), strAmt));
        }
        this.tickDuration();
    }
}
