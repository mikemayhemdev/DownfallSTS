  package reskinContent.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 
 public class PortraitWindyParticleEffect extends AbstractGameEffect {
   private float scaleY;
   private float x;
   private float y;
   private float vX;
   private float vY;


   public PortraitWindyParticleEffect(Color setColor, boolean reverse) {
     if (!reverse) {

       this.x = MathUtils.random(-400.0F, -100.0F) * Settings.scale - 128.0F;
       this.vX = MathUtils.random(1500.0F, 2500.0F) * Settings.scale;
     } else {

       this.x = Settings.WIDTH + MathUtils.random(400.0F, 100.0F) * Settings.scale - 128.0F;
       this.vX = MathUtils.random(-1500.0F, -2500.0F) * Settings.scale;
     }

     this.y = MathUtils.random(0.0F, 1.0F) * Settings.HEIGHT - 128.0F * Settings.scale;
     this.vY = MathUtils.random(-100.0F, 100.0F) * Settings.scale;

     this.duration = 2.0F;
     this.scale = MathUtils.random(1.5F, 3.0F);

     this.vX *= this.scale;
     this.scale *= Settings.scale;
     this.scaleY = MathUtils.random(0.5F, 2.0F) * Settings.scale;

     this.color = setColor.cpy();
     this.color.a = MathUtils.random(0.5F, 1.0F);
     if (this.scaleY < 1.0F * Settings.scale)
       this.renderBehind = true; 
   }

   
   public PortraitWindyParticleEffect() {
     this.x = MathUtils.random(-400.0F, -100.0F) * Settings.scale - 128.0F;
     this.y = MathUtils.random(0.0F, 1.0F) * Settings.HEIGHT - 128.0F * Settings.scale;
     this.vX = MathUtils.random(1500.0F, 2500.0F) * Settings.scale;
     this.vY = MathUtils.random(-100.0F, 100.0F) * Settings.scale;
     this.duration = 2.0F;
     this.scale = MathUtils.random(1.5F, 3.0F);
     this.vX *= this.scale;
     this.scale *= Settings.scale;
     this.scaleY = MathUtils.random(0.5F, 2.0F) * Settings.scale;
     this.color = new Color(0.9F, 0.9F, 1.0F, MathUtils.random(0.5F, 1.0F));
     if (this.scaleY < 1.0F * Settings.scale) {
       this.renderBehind = true;
     }
   }
   
   public void update() {
     this.x += this.vX * Gdx.graphics.getDeltaTime();
     this.y += this.vY * Gdx.graphics.getDeltaTime();
     this.duration -= Gdx.graphics.getDeltaTime();
     if (this.duration < 0.0F) {
       this.isDone = true;
     }
   }
   
   public void render(SpriteBatch sb) {
     sb.setBlendFunction(770, 1);
     sb.setColor(this.color);
     sb.draw(ImageMaster.HORIZONTAL_LINE, this.x, this.y, 128.0F, 128.0F, 256.0F, 256.0F, this.scale * 

         MathUtils.random(0.7F, 1.3F), this.scaleY * MathUtils.random(0.7F, 1.3F), this.rotation, 0, 0, 256, 256, false, false);

     sb.setBlendFunction(770, 771);
   }
   
   public void dispose() {}
 }


