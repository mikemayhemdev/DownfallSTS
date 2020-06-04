 package reskinContent.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect;

 
 public class PortraitGhostlyWeakFireEffect extends GhostlyWeakFireEffect {
   public PortraitGhostlyWeakFireEffect(float x, float y ,float scale) {
     super(x,y);
     this.scale *=scale;
   }
 }


