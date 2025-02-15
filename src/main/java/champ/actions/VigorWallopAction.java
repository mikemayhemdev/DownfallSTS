package champ.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import downfall.powers.NextTurnPowerPower;
import hermit.util.Wiz;

public class VigorWallopAction extends AbstractGameAction {
    private int damage;

    public VigorWallopAction(AbstractCreature target, AbstractCreature source, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        setValues(target, source, amount);
        this.damage = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        tickDuration();
        if (this.isDone) {
            vigorattack();
            this.target.damage(new DamageInfo(this.source, this.damage, this.damageType));
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
                AbstractDungeon.actionManager.addToTop((AbstractGameAction) new WaitAction(0.1F));
        }
    }

    private void vigorattack() {
        int tmp = this.damage;
        tmp -= this.target.currentBlock;
        if (tmp > this.target.currentHealth)
            tmp = this.target.currentHealth;
        if (tmp > 0) {
            Wiz.atb(new ApplyPowerAction(this.source, this.source, new NextTurnPowerPower(this.source, new VigorPower(this.source,tmp)), tmp));
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(this.source, new FlameBarrierEffect(this.source.hb.cX, this.source.hb.cY), 0.1F));
            } else {
                this.addToBot(new VFXAction(this.source, new FlameBarrierEffect(this.source.hb.cX, this.source.hb.cY), 0.5F));
            }
        }
    }
}