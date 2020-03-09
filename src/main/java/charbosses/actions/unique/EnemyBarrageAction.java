package charbosses.actions.unique;


import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class EnemyBarrageAction extends AbstractGameAction {
    private DamageInfo info = null;
    private AbstractCreature target;

    public EnemyBarrageAction(AbstractCreature m, DamageInfo info) {
        this.info = info;
        this.target = m;
    }

    public void update() {
        for(int i = 0; i < AbstractCharBoss.boss.orbs.size(); ++i) {
            if (!(AbstractCharBoss.boss.orbs.get(i) instanceof EmptyOrbSlot)) {
                this.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT, true));
            }
        }

        this.isDone = true;
    }
}
