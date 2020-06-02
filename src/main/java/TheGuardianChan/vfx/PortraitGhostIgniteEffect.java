 package TheGuardianChan.vfx;
 
 import TheGuardianChan.patches.CharacterSelectScreenPatches;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;

 
 public class PortraitGhostIgniteEffect extends GhostIgniteEffect {
   private static final int COUNT = 25;
    private float x;
    private float y;
    private float scale;
   public PortraitGhostIgniteEffect(float x, float y,float scale) {
     super(x,y);
     this.x = x;
     this.y = y;
     this.scale = scale;
   }

   public void update() {
     for (int i = 0; i < 25; i++) {
         CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitFireBurstParticleEffect(this.x, this.y,this.scale));
         CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitLightFlareParticleEffect(this.x, this.y,this.scale, Color.CHARTREUSE));
     } 
     this.isDone = true;
   }
   
   public void render(SpriteBatch sb) {}
   
   public void dispose() {}
 }


