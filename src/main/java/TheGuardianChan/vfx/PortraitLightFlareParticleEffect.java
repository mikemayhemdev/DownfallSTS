 package TheGuardianChan.vfx;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.math.Interpolation;
 import com.badlogic.gdx.math.MathUtils;
 import com.badlogic.gdx.math.Vector2;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

 
 public class PortraitLightFlareParticleEffect extends LightFlareParticleEffect {

   public PortraitLightFlareParticleEffect(float x, float y,float scale, Color color) {
       super(x,y,color);
     this.scale *= scale;
   }
 }


