package charbosses.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class EnemyBaneAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private AbstractCreature m;

    public EnemyBaneAction(AbstractCreature target, DamageInfo info) {
        this.info = info;// 18
        this.setValues(target, info);// 19
        this.m = target;// 20
        this.actionType = ActionType.DAMAGE;// 21
        this.attackEffect = AttackEffect.SLASH_VERTICAL;// 22
        this.duration = 0.01F;// 23
    }// 24

    public void update() {
        if (this.target == null) {// 28
            this.isDone = true;// 29
        } else {
            if (this.m.hasPower("Poison")) {// 32
                if (this.duration == 0.01F && this.target != null && this.target.currentHealth > 0) {// 33
                    if (this.info.type != DamageType.THORNS && this.info.owner.isDying) {// 35 36
                        this.isDone = true;// 37
                        return;// 38
                    }

                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));// 41
                }

                this.tickDuration();// 44
                if (this.isDone && this.target != null && this.target.currentHealth > 0) {// 46
                    this.target.damage(this.info);// 47
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 49
                        AbstractDungeon.actionManager.clearPostCombatActions();// 50
                    }

                    this.addToTop(new WaitAction(0.1F));// 52
                }
            } else {
                this.isDone = true;// 56
            }

        }
    }// 30 58
}
