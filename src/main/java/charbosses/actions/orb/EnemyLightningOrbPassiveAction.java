package charbosses.actions.orb;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;


public class EnemyLightningOrbPassiveAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractOrb orb;
    private boolean hitAll;

    public EnemyLightningOrbPassiveAction(DamageInfo info, AbstractOrb orb, boolean hitAll) {
        this.info = info;
        this.orb = orb;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
        this.hitAll = hitAll;
    }

    public void update() {
        AbstractCreature t = AbstractDungeon.player;

        float speedTime = 0.2F / (float) AbstractCharBoss.boss.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0F;
        }

        this.info.output = AbstractOrb.applyLockOn(t, this.info.base);
        this.addToTop(new DamageAction(t, this.info, AttackEffect.NONE, true));
        this.addToTop(new VFXAction(new LightningEffect(t.drawX, t.drawY), speedTime));
        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        if (this.orb != null) {
            this.addToTop(new VFXAction(new OrbFlareEffect(this.orb, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
        }

        this.isDone = true;
    }
}