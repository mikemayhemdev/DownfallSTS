 package reskinContent.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.combat.WobblyLineEffect;
 import reskinContent.patches.CharacterSelectScreenPatches;

 public class PortraitIntimidateEffect
   extends AbstractGameEffect {
   private static final float EFFECT_DUR = 1.0F;
   private float x;
   
   public PortraitIntimidateEffect(float newX, float newY) {
     this.duration = 1.0F;
     this.x = newX;
     this.y = newY;
   }
   private float y; private float vfxTimer; private static final float VFX_INTERVAL = 0.016F;
   public void update() {
     this.duration -= Gdx.graphics.getDeltaTime();
     this.vfxTimer -= Gdx.graphics.getDeltaTime();
     if (this.vfxTimer < 0.0F) {
       this.vfxTimer = 0.032F;
         CharacterSelectScreenPatches.char_effectsQueue.add(new WobblyLineEffect(this.x, this.y, Settings.CREAM_COLOR.cpy()));
     } 
     if (this.duration < 0.0F)
       this.isDone = true; 
   }
   
   public void render(SpriteBatch sb) {}
   
   public void dispose() {}
 }


