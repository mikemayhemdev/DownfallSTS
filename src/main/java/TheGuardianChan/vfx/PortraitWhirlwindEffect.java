 package TheGuardianChan.vfx;
 import TheGuardianChan.patches.CharacterSelectScreenPatches;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
 import com.megacrit.cardcrawl.vfx.combat.WindyParticleEffect;

 public class PortraitWhirlwindEffect extends AbstractGameEffect {
   private int count;
   private float timer;
   private boolean reverse;
   private int hitCount;
   private int particleAmount;

   public PortraitWhirlwindEffect(Color setColor, boolean reverse) {
     this.count = 0;
     this.timer = 0.0F;
     this.reverse = false;
     this.color = setColor.cpy();
     this.reverse = reverse;
     this.hitCount = 4;
     this.particleAmount = 9;


   }

   
   public PortraitWhirlwindEffect() {
       this(new Color(0.9F, 0.9F, 1.0F, 1.0F), false);
   }
 
   
   public void update() {
     this.timer -= Gdx.graphics.getDeltaTime();

     if (this.timer < 0.0F) {
       this.timer += 0.05F;
       
       if (this.count == 0) {
           CardCrawlGame.sound.play("ATTACK_WHIRLWIND");
           CharacterSelectScreenPatches.char_effectsQueue.add(new BorderLongFlashEffect(this.color.cpy()));
       }
       if( this.count % particleAmount == 0){
           CardCrawlGame.sound.play("ATTACK_HEAVY");
       }

         CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitWindyParticleEffect(this.color, this.reverse));
       this.count++;
       
       if (this.count >= particleAmount * this.hitCount)
         this.isDone = true; 
     } 
   }
   
   public void render(SpriteBatch sb) {}
   
   public void dispose() {}
 }


