/*    */
package slimebound.vfx;
/*    */
/*    */

import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

/*    */
/*    */

/*    */
/*    */ public class SearEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect
        /*    */ {
    /*    */   private static final float FIREBALL_INTERVAL = 0.016F;
    /*    */   private float x;
    /*    */   private float y;
    /*    */   private float startX;
    /*    */   private float startY;
    /*    */   private float targetX;
    /*    */   private float targetY;
    /* 16 */   private float vfxTimer = 0.0F;

    /*    */
    /*    */
    public SearEffect(float startX, float startY, float targetX, float targetY) {
        /* 19 */
        this.startingDuration = 0.5F;
        /* 20 */
        this.duration = 0.5F;
        /* 21 */
        this.startX = startX;
        /* 22 */
        this.startY = startY;
        /* 23 */
        this.targetX = (targetX + com.badlogic.gdx.math.MathUtils.random(-20.0F, 20.0F) * com.megacrit.cardcrawl.core.Settings.scale);
        /* 24 */
        this.targetY = (targetY + com.badlogic.gdx.math.MathUtils.random(-20.0F, 20.0F) * com.megacrit.cardcrawl.core.Settings.scale);
        /* 25 */
        this.x = startX;
        /* 26 */
        this.y = startY;
        /*    */
    }

    /*    */
    /*    */
    public void update() {
        /* 30 */
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.duration / this.startingDuration);
        /* 31 */
        this.y = Interpolation.fade.apply(this.targetY, this.startY, this.duration / this.startingDuration);
        /*    */
        /* 33 */
        /* 36 */
        AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, com.badlogic.gdx.graphics.Color.CHARTREUSE));
        /* 37 */
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.FireBurstParticleEffect(this.x, this.y));
        /*    */
        /*    */
        /* 40 */
        this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        /* 41 */
        if (this.duration < 0.0F) {
            /* 42 */
            this.isDone = true;
            /* 43 */
            AbstractDungeon.effectsQueue.add(new GhostIgniteEffect(this.x, this.y));
            /* 44 */
            AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect(this.x, this.y));
            /*    */
        }
        /*    */
    }

    /*    */
    /*    */
    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
    }


    public void dispose() {
        this.isDone = true;

    }

}

