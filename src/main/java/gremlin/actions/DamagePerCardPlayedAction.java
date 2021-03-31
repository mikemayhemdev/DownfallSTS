package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;

public class DamagePerCardPlayedAction extends AbstractGameAction
{
    private DamageInfo info;

    public DamagePerCardPlayedAction(final AbstractCreature target, final DamageInfo info, final AttackEffect effect) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public DamagePerCardPlayedAction(final AbstractCreature target, final DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }

    @Override
    public void update() {
        this.isDone = true;
        if (this.target != null && this.target.currentHealth > 0) {
            int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            --count;
            for (int i = 0; i < count; ++i) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            }
        }
    }
}

