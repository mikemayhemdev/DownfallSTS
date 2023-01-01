package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class AllEnemyLoseHPAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;

    public AllEnemyLoseHPAction(int amount) {
        this(amount, AttackEffect.NONE);
    }

    public AllEnemyLoseHPAction(int amount, AttackEffect effect) {
        this.source = AbstractDungeon.player;
        this.amount = amount;

        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.startDuration = this.duration = Settings.FAST_MODE ? 0.1f : DURATION;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped()) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                }
            }
        }

        this.tickDuration();

        if (this.isDone) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped() && !m.halfDead) {
                    m.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}