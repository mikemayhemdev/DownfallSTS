package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;


public class DestroyOrbSlotForDamageAction extends AbstractGameAction {
    private int damage;
    private AbstractOrb o;

    public DestroyOrbSlotForDamageAction(int damage, AbstractOrb o) {
        this.actionType = ActionType.DAMAGE;
        this.damage = damage;
        this.o = o;
    }

    public void update() {
        AbstractDungeon.topLevelEffectsQueue.add(new ExplosionSmallEffect(o.hb.cX, o.hb.cY));

        addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AttackEffect.FIRE));

        this.isDone = true;
    }
}
