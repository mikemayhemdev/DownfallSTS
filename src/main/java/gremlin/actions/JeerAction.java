package gremlin.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class JeerAction extends AbstractGameAction
{
    public JeerAction(final AbstractCreature target, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null) {
            for(AbstractPower p : this.target.powers){
                if(p.type == AbstractPower.PowerType.DEBUFF){
                    AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, amount));
                    break;
                }
            }
        }
        this.tickDuration();
    }
}
