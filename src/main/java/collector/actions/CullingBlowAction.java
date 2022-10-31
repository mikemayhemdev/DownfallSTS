package collector.actions;

import collector.powers.Suffering;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class CullingBlowAction extends AbstractGameAction {
    private int increaseAmount;
    private DamageInfo info;
    public int[] damage;
    public int threshold;
    public CullingBlowAction(AbstractCreature target, int[] amount, int threshold) {
        this.damage = amount;
        this.setValues(target, AbstractDungeon.player,damage[0]);
        this.actionType = ActionType.DAMAGE;
        this.threshold = threshold;
        this.duration = 0.1F;
    }

    public void update() {
        int i;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean playedMusic = false;
            int mo = AbstractDungeon.getCurrRoom().monsters.monsters.size();

            for( i = 0; i < mo; ++i) {
                if (!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying && ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(mo-1)).currentHealth > 0 && !((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect));
                    }
                }
            }
        }

        this.tickDuration();
        if (this.isDone) {

            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }


            for(i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
                AbstractMonster target = (AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!target.isDying && target.currentHealth > threshold && !target.isEscaping) {
                    target.damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                    if ((this.target.isDying || this.target.currentHealth <= threshold) && !this.target.halfDead) {
                        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new Suffering(1,target)));
                        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new WeakPower(target,1,false)));
                        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new VulnerablePower(target,1,false)));
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            this.addToTop(new WaitAction(0.1F));
        }

    }
}
