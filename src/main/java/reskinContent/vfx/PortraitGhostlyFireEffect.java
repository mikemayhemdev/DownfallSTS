 package reskinContent.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;


 public class PortraitGhostlyFireEffect extends GhostlyFireEffect {
   private TextureAtlas.AtlasRegion img;
   private float x;
   private float y;
   private float vX;
   private float vY;
   private static final float DUR = 1.0F;
   
   public PortraitGhostlyFireEffect(float x, float y,float scale) {
     super(x,y);

     this.vX *= scale;
     this.vY *=  scale;
     this.scale *= scale;
   }


 }


