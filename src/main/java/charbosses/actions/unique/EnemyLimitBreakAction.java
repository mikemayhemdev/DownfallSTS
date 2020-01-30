package charbosses.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.bosses.AbstractCharBoss;

public class EnemyLimitBreakAction extends AbstractGameAction
{
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
