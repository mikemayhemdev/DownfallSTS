/*    */ package theHexaghost.vfx;
/*    */
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.math.MathUtils;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import java.util.ArrayList;
/*    */
/*    */ public class ExplosionSmallEffectGreen extends com.megacrit.cardcrawl.vfx.AbstractGameEffect
        /*    */ {
    /*    */   private static final int EMBER_COUNT = 12;
    /*    */   private float x;
    /*    */   private float y;
    /*    */
    /*    */   public ExplosionSmallEffectGreen(float x, float y)
    /*    */   {
        /* 16 */     this.x = x;
        /* 17 */     this.y = y;
        /*    */   }
    /*    */
    /*    */   public void update()
    /*    */   {
        /* 22 */     com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect(this.x, this.y));
        /* 23 */     for (int i = 0; i < 12; i++) {
            /* 24 */       com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue.add(new SmokingEmberEffectGreen(this.x +
                    /*    */
                    /* 26 */         MathUtils.random(-50.0F, 50.0F) * Settings.scale, this.y +
                    /* 27 */         MathUtils.random(-50.0F, 50.0F) * Settings.scale));
            /*    */     }
        /* 29 */     com.megacrit.cardcrawl.core.CardCrawlGame.sound.playA("ATTACK_FIRE", MathUtils.random(-0.2F, -0.1F));
        /* 30 */     this.isDone = true;
        /*    */   }
    /*    */
    /*    */   public void render(SpriteBatch sb) {}
    /*    */
    /*    */   public void dispose() {}
    /*    */ }


/* Location:              D:\SteamLibrary\steamapps\common\SlayTheSpire\desktop-1.0.jar!\com\megacrit\cardcrawl\vfx\combat\ExplosionSmallEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */