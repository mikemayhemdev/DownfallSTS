package charbosses.actions.orb;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import java.util.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class EnemyDarkOrbEvokeAction extends AbstractGameAction
{
    private DamageInfo info;
    private static final float DURATION = 0.1f;
    private static final float POST_ATTACK_WAIT_DUR = 0.1f;
    private boolean muteSfx;
    
    public EnemyDarkOrbEvokeAction(final DamageInfo info, final AttackEffect effect) {
        this.muteSfx = false;
        this.setValues(AbstractDungeon.player, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1f;
    }
    
    @Override
    public void update() {
        if ((this.shouldCancelAction() && this.info.type != DamageInfo.DamageType.THORNS) || this.target == null) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.1f) {
            this.info.output = AbstractOrb.applyLockOn(this.target, this.info.base);
            if (this.info.type != DamageInfo.DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));
        }
        this.tickDuration();
        if (this.isDone) {
            if (this.attackEffect == AttackEffect.POISON) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            else if (this.attackEffect == AttackEffect.FIRE) {
                this.target.tint.color = Color.RED.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            this.target.damage(this.info);
            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1f));
            }
        }
    }
}
