package charbosses.actions.orb;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;


public class EnemyLightningOrbEvokeAction extends AbstractGameAction {
    private DamageInfo info;
    private boolean hitAll;

    public EnemyLightningOrbEvokeAction(DamageInfo info, boolean hitAll) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
        this.hitAll = hitAll;
    }

    public void update() {
        //if (!this.hitAll) {
        AbstractCreature t = AbstractDungeon.player;
        float speedTime = 0.1F;
        if (!AbstractDungeon.player.orbs.isEmpty()) {
            speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
        }

        if (Settings.FAST_MODE) {
            speedTime = 0.0F;
        }

        this.info.output = AbstractOrb.applyLockOn(t, this.info.base);
        this.addToTop(new DamageAction(t, this.info, AttackEffect.NONE, true));
        this.addToTop(new VFXAction(new LightningEffect(t.drawX, t.drawY), speedTime));
        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        //}

        this.isDone = true;
    }
}