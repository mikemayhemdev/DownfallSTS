package charbosses.actions.unique;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;


public class CustomReaperAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    public int[] damage;

    public CustomReaperAction(AbstractCreature source, int[] amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect) {
        setValues(null, source, amount[0]);
        this.damage = amount;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        boolean playedMusic;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for (int i = 0; i < temp; i++) {
                if ((!(AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying) &&
                        ((AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).currentHealth > 0) &&
                        (!(AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping)) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(

                                (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX,
                                (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(

                                (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX,
                                (AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect));
                    }
                }
            }
        }


        tickDuration();

        if (this.isDone) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }

            int healAmount = 0;
            for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
                AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (target != source && (!target.isDying) && (target.currentHealth > 0) && (!target.isEscaping)) {
                    target.damage(new com.megacrit.cardcrawl.cards.DamageInfo(this.source, this.damage[i], this.damageType));
                    if (target.lastDamageTaken > 0) {
                        healAmount += target.lastDamageTaken;
                        for (int j = 0; (j < target.lastDamageTaken / 2) && (j < 10); j++) {
                            addToBot(new com.megacrit.cardcrawl.actions.animations.VFXAction(new com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                        }
                    }
                }
            }

            AbstractPlayer targetPlayer = AbstractDungeon.player;
            if ((!targetPlayer.isDying) && (targetPlayer.currentHealth > 0) && (!targetPlayer.isEscaping)) {
                targetPlayer.damage(new com.megacrit.cardcrawl.cards.DamageInfo(this.source, this.damage[0], this.damageType));
                if (targetPlayer.lastDamageTaken > 0) {
                    healAmount += targetPlayer.lastDamageTaken;
                    for (int j = 0; (j < targetPlayer.lastDamageTaken / 2) && (j < 10); j++) {
                        addToBot(new com.megacrit.cardcrawl.actions.animations.VFXAction(new com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect(targetPlayer.hb.cX, targetPlayer.hb.cY)));
                    }
                }
            }

            if (healAmount > 0) {
                if (!Settings.FAST_MODE) {
                    addToBot(new WaitAction(0.3F));
                }
                addToBot(new com.megacrit.cardcrawl.actions.common.HealAction(this.source, this.source, healAmount));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            addToTop(new WaitAction(0.1F));
        }
    }
}

