package slimebound.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.vfx.LeechEffect;

public class VampireIntoBlockDamageAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private DamageInfo info;

    public VampireIntoBlockDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        tickDuration();

        if (this.isDone) {
            heal(this.info);
            this.target.damage(this.info);

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
    }


    private void heal(DamageInfo info) {
        int healAmount = info.output;
        if (healAmount < 0) {
            return;
        }

        healAmount -= this.target.currentBlock;

        if (healAmount > this.target.currentHealth) {
            healAmount = this.target.currentHealth;
        }

        if (healAmount > 0) {
            if ((healAmount > 1) && (this.target.hasPower("Buffer"))) {
                return;
            }

            if ((healAmount > 1) && (this.target.hasPower("Intangible"))) {
                healAmount = 1;
            }


            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.GainBlockAction(this.source, this.source, healAmount));


            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.WaitAction(0.1F));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new LeechEffect(this.target.hb.cX, this.target.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 5, new Color(0.6F, 0.6F, 1F, 1F)), 0.25F));

        }
    }
}

