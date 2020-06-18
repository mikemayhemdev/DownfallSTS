/*    */ package theHexaghost.vfx;
/*    */
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.Graphics;
/*    */
/*    */ public class SmokingEmberEffectGreen extends com.megacrit.cardcrawl.vfx.AbstractGameEffect
        /*    */ {
    /*    */   private float x;
    /*    */   private float vX;
    /*    */   private float y;
    /*    */   private float vY;
    /*    */   private float gravity;
    /*    */   private static final float INTERVAL = 0.01F;
    /* 14 */   private float smokeTimer = 0.0F;
    /*    */
    /*    */   public SmokingEmberEffectGreen(float x, float y) {
        /* 17 */     this.x = x;
        /* 18 */     this.y = y;
        /* 19 */     this.vX = (com.badlogic.gdx.math.MathUtils.random(-600.0F, 600.0F) * com.megacrit.cardcrawl.core.Settings.scale);
        /* 20 */     this.vY = (com.badlogic.gdx.math.MathUtils.random(-200.0F, 600.0F) * com.megacrit.cardcrawl.core.Settings.scale);
        /* 21 */     this.gravity = (800.0F * com.megacrit.cardcrawl.core.Settings.scale);
        /*    */
        /* 23 */     this.scale = (com.badlogic.gdx.math.MathUtils.random(0.2F, 0.4F) * com.megacrit.cardcrawl.core.Settings.scale);
        /* 24 */     this.duration = com.badlogic.gdx.math.MathUtils.random(0.3F, 0.6F);
        /*    */   }
    /*    */
    /*    */   public void update()
    /*    */   {
        /* 29 */     this.x += this.vX * Gdx.graphics.getDeltaTime();
        /* 30 */     this.y += this.vY * Gdx.graphics.getDeltaTime();
        /* 31 */     this.vY -= this.gravity * Gdx.graphics.getDeltaTime();
        /*    */
        /* 33 */     this.smokeTimer -= Gdx.graphics.getDeltaTime();
        /* 34 */     if (this.smokeTimer < 0.0F) {
            /* 35 */       this.smokeTimer = 0.01F;
            /* 36 */       com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue.add(new FastSmokeParticleGreen(this.x, this.y));
            /*    */     }
        /*    */
        /* 39 */     this.duration -= Gdx.graphics.getDeltaTime();
        /*    */
        /* 41 */     if (this.duration < 0.0F) {
            /* 42 */       this.isDone = true;
            /*    */     }
        /*    */   }
    /*    */
    /*    */   public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {}
    /*    */
    /*    */   public void dispose() {}
    /*    */ }


/* Location:              D:\SteamLibrary\steamapps\common\SlayTheSpire\desktop-1.0.jar!\com\megacrit\cardcrawl\vfx\combat\SmokingEmberEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */