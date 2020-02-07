package charbosses.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;

public class EnemyDamagePerAttackPlayedAction extends AbstractGameAction
{
    private DamageInfo info;
    
    public EnemyDamagePerAttackPlayedAction(final AbstractCreature target, final DamageInfo info, final AttackEffect effect) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }
    
    public EnemyDamagePerAttackPlayedAction(final AbstractCreature target, final DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }
    
    @Override
    public void update() {
        this.isDone = true;
        if (this.target != null && this.target.currentHealth > 0) {
            int count = AbstractCharBoss.boss.attacksPlayedThisTurn;
            --count;
            for (int i = 0; i < count; ++i) {
                this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            }
        }
    }
}
