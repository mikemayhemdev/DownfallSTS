/*    */ package theHexaghost.vfx;
/*    */
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.Graphics;
/*    */ import com.badlogic.gdx.graphics.Color;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
/*    */ import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/*    */
/*    */ public class FastSmokeParticleGreen extends AbstractGameEffect
        /*    */ {
    /*    */   private float x;
    /*    */   private float y;
    /*    */   private float vX;
    /* 14 */   private float scale = 0.01F;
    /*    */   private float targetScale;
    /*    */   private static TextureAtlas.AtlasRegion img;
    /*    */
    /* 18 */   public FastSmokeParticleGreen(float x, float y) { if (img == null) {
        /* 19 */       img = com.megacrit.cardcrawl.helpers.ImageMaster.EXHAUST_L;
        /*    */     }
        /*    */
        /* 22 */     this.targetScale = (MathUtils.random(0.3F, 0.6F) * com.megacrit.cardcrawl.core.Settings.scale);
        /* 23 */     this.color = new Color(MathUtils.random(0.8F, 1.0F), 1.0F, MathUtils.random(0.5F, 0.8F), 1.0F);
        /*    */
        /* 25 */     this.x = (x - img.packedWidth / 2.0F);
        /* 26 */     this.y = (y - img.packedHeight / 2.0F);
        /* 27 */     this.rotation = MathUtils.random(360.0F);
        /* 28 */     this.duration = 0.6F;
        /*    */   }
    /*    */
    /*    */   public void update() {
        /* 32 */     if (this.color.b > 0.1F) {
            /* 33 */       this.color.b -= Gdx.graphics.getDeltaTime() * 4.0F;
            /* 34 */       this.color.r -= Gdx.graphics.getDeltaTime() * 3.0F;
            /* 35 */       this.color.g -= Gdx.graphics.getDeltaTime() * 0.5F;
            /* 36 */     } else if (this.color.r > 0.1F) {
            /* 37 */       this.color.r -= Gdx.graphics.getDeltaTime() * 4.0F;
            /* 38 */     } else if (this.color.g > 0.1F) {
            /* 39 */       this.color.g -= Gdx.graphics.getDeltaTime() * 4.0F;
            /*    */     }
        /* 41 */     if (this.color.b < 0.0F) {
            /* 42 */       this.color.b = 0.0F;
            /*    */     }
        /* 44 */     if (this.color.r < 0.0F) {
            /* 45 */       this.color.r = 0.0F;
            /*    */     }
        /* 47 */     if (this.color.g < 0.0F) {
            /* 48 */       this.color.g = 0.0F;
            /*    */     }
        /*    */
        /* 51 */     this.scale = com.badlogic.gdx.math.Interpolation.swingIn.apply(this.targetScale, 0.1F, this.duration / 0.6F);
        /* 52 */     this.rotation += this.vX * 2.0F * Gdx.graphics.getDeltaTime();
        /* 53 */     this.color.a = this.duration;
        /*    */
        /* 55 */     this.duration -= Gdx.graphics.getDeltaTime();
        /* 56 */     if (this.duration < 0.0F) {
            /* 57 */       this.isDone = true;
            /*    */     }
        /*    */   }
    /*    */
    /*    */   public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
        /* 62 */     sb.setColor(this.color);
        /* 63 */     sb.draw(img, this.x, this.y, img.packedWidth / 2.0F, img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, this.scale, this.scale, this.rotation);
        /*    */   }
    /*    */
    /*    */   public void dispose() {}
    /*    */ }


/* Location:              D:\SteamLibrary\steamapps\common\SlayTheSpire\desktop-1.0.jar!\com\megacrit\cardcrawl\vfx\FastSmokeParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */