package guardian.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.math.Interpolation;
 import com.badlogic.gdx.math.MathUtils;
 import com.badlogic.gdx.math.Vector2;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 
 public class DefensiveModeStanceParticleEffect
   extends AbstractGameEffect {
   private static final float EFFECT_DUR = 1.0F;
   private float x;
   private float y;
   private float speed;
   private float speedStart;
   private float speedTarget;
   private float scaleXMod;
   private float flipper;
   private TextureAtlas.AtlasRegion img;
   private float dur_div2 = this.duration / 2.0F;

   public DefensiveModeStanceParticleEffect() {
     this.img = ImageMaster.GLOW_SPARK;
     this.rotation = MathUtils.random(360.0F);
     this.scale = MathUtils.random(1.4F, 1.8F);
     this.scaleXMod = MathUtils.random(0.0F, 1.5F);
     this.x = AbstractDungeon.player.hb.cX - this.img.packedWidth / 2.0F;
     this.y = AbstractDungeon.player.hb.cY - this.img.packedHeight / 2.0F;
     this.duration = 1.0F;
     this.color = new Color(1.0F, 0.9F, 0.7F, 0.0F);
     this.renderBehind = MathUtils.randomBoolean();
     this.speedStart = MathUtils.random(80.0F, 160.0F) * Settings.scale;
     this.speedTarget = 200.0F * Settings.scale;
     this.speed = this.speedStart;


     
     if (MathUtils.randomBoolean()) {
       this.flipper = 90.0F;
     } else {
       this.flipper = 270.0F;
     } 
     color.g -= MathUtils.random(0.1F);
     color.b -= MathUtils.random(0.2F);
     color.a = 0.0F;
   }

   public void update() {
     Vector2 tmp = new Vector2(MathUtils.cosDeg(this.rotation), MathUtils.sinDeg(this.rotation));
     tmp.x *= this.speed * Gdx.graphics.getDeltaTime();
     tmp.y *= this.speed * Gdx.graphics.getDeltaTime();
     this.speed = Interpolation.fade.apply(this.speedStart, this.speedTarget, 1.0F - this.duration / 2.0F);
     this.x += tmp.x;
     this.y += tmp.y;

     this.scale -= Gdx.graphics.getDeltaTime();
     this.duration -= Gdx.graphics.getDeltaTime();

     if (this.duration < 0.0F) {
       this.isDone = true;
     } else if (this.duration > 0.25F) {
       this.color.a = Interpolation.fade.apply(0.0F, 0.7F, (1.0F - this.duration) );
     } else if (this.duration < 0.25F) {
       this.color.a = Interpolation.fade.apply(0.0F, 0.7F, this.duration * 3 );
     }
   }
 
   
   public void render(SpriteBatch sb) {
     sb.setBlendFunction(770, 1);
     sb.setColor(this.color);
     sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight,
             (this.scale + MathUtils.random(-0.08F, 0.08F)) * Settings.scale,
             (this.scale + this.scaleXMod + MathUtils.random(-0.08F, 0.08F)) * Settings.scale,
             this.rotation + this.flipper + MathUtils.random(-3.0F, 3.0F));
     sb.setBlendFunction(770, 771);
   }
   
   public void dispose() {}
 }


