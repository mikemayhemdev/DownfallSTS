package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BurningHitAction extends AbstractGameAction {
    private int damage;

    public BurningHitAction(AbstractCreature target, AbstractCreature source, int amount, DamageInfo.DamageType type, AttackEffect effect) {
        setValues(target, source, amount);
        damage = amount;
        actionType = ActionType.DAMAGE;
        damageType = type;
        attackEffect = effect;
    }

    @Override
    public void update() {
        if (duration == 0.5f) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
        }
        tickDuration();

        if (isDone) {
            TOASTY();

            target.damage(new DamageInfo(source, damage, damageType));

            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
        }
    }

    private void TOASTY() {
        int tmp = damage;
        tmp -= target.currentBlock;
        if (tmp > target.currentHealth) {
            tmp = target.currentHealth;
        }
        if (tmp > 0) {
            AbstractDungeon.actionManager.addToBottom(new BurnAction(((AbstractMonster)target), tmp));
        }
    }
}