package charbosses.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;

public class EnemyBouncingFlaskAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private int numTimes;
    private int amount;
    private AbstractMonster source;

    public EnemyBouncingFlaskAction(int amount, AbstractMonster source, int numTimes) {
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.DEBUFF;// 20
        this.duration = 0.01F;// 21
        this.numTimes = numTimes;// 22
        this.amount = amount;// 23
        this.source = source;
    }// 24

    public void update() {
        if (this.target == null) {// 28
            this.isDone = true;// 29
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 33
            AbstractDungeon.actionManager.clearPostCombatActions();// 34
            this.isDone = true;// 35
        } else {
            if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 40
                --this.numTimes;// 41
                this.addToTop(new EnemyBouncingFlaskAction(this.amount, source, this.numTimes));// 46
                this.addToTop(new VFXAction(new PotionBounceEffect(this.target.hb.cX, this.target.hb.cY, target.hb.cX, target.hb.cY), 0.4F));// 47
            }

            if (this.target.currentHealth > 0) {// 53
                this.addToTop(new ApplyPowerAction(this.target, this.source, new EnemyPoisonPower(this.target, source, this.amount), this.amount, true, AttackEffect.POISON));// 54
                this.addToTop(new WaitAction(0.1F));// 62
            }

            this.isDone = true;// 65
        }
    }// 30 36 66
}
