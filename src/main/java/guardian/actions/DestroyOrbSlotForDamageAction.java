package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
        AbstractPlayer p = AbstractDungeon.player;


        AbstractDungeon.topLevelEffectsQueue.add(new ExplosionSmallEffect(o.hb.cX, o.hb.cY));

        DamageInfo damageInfo = new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS);
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, damageInfo, AbstractGameAction.AttackEffect.FIRE, true));
            }

        }

        this.isDone = true;
    }
}
