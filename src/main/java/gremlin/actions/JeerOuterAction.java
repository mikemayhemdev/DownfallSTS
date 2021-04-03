package gremlin.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class JeerOuterAction extends AbstractGameAction
{
    private final DamageInfo info;

    public JeerOuterAction(final AbstractCreature target, final DamageInfo info, final int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.amount = amount;
        this.info = info;
    }

    @Override
    public void update() {
        if (this.target != null) {
            for(AbstractPower p : this.target.powers){
                if(p.type == AbstractPower.PowerType.DEBUFF){
                    AbstractDungeon.actionManager.addToTop(new JeerFollowUpAction(target, amount));
                    break;
                }
            }
        }
        AbstractDungeon.actionManager.addToTop(new JeerAction(target,amount));
        AbstractDungeon.actionManager.addToTop(new DamageAction(target, info, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }
}
