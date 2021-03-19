 package reskinContent.helpers;
 
 import reskinContent.patches.CharacterSelectScreenPatches;
 import reskinContent.reskinContent;
 import reskinContent.vfx.PortraitGhostIgniteEffect;
 import reskinContent.vfx.PortraitGhostlyFireEffect;
 import reskinContent.vfx.PortraitGhostlyWeakFireEffect;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.MathHelper;
 import com.megacrit.cardcrawl.localization.MonsterStrings;
 import com.megacrit.cardcrawl.monsters.exordium.HexaghostOrb;
 import com.megacrit.cardcrawl.vfx.BobEffect;
 import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;
 import com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect;
 import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
 
 public class PortraitHexaghostOrb {
     private BobEffect effect;
     private float activateTimer;
     public boolean activated;
     public boolean hidden;
     public boolean playedSfx;
     private Color color;
     private float x;
     private float y;
     private float scale = 3.0f;
     private int index;
     private float particleTimer;
     private float rotation;
     private boolean hellFlame;
     private float hellFlameTimer;
     private static final float PARTICLE_INTERVAL = 0.06F;

    public PortraitHexaghostOrb(float x, float y, int index) {
        this.effect = new BobEffect(2.0F);
        this.activated = false;
        this.hidden = false;
        this.playedSfx = false;
        this.particleTimer = 0.0F;

        this.x = x * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;
        this.y = y * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;
        this.activateTimer = index * 0.3F;
        this.color = Color.CHARTREUSE.cpy();
        this.color.a = 0.0F;

        this.index = index;
        this.hidden = false;
        this.rotation = 0.0f;
        this.hellFlame = false;
        this.hellFlameTimer = 2.0f;
    }


     public void activate(float oX, float oY) {
         this.playedSfx = false;
         this.activated = true;
         this.hidden = false;

     }


     public void deactivate() {
         this.activated = false;
     }

     public void hide() { this.hidden = true; }

     public void hellFlameActivate() { this.hellFlame = true; }

   public void update(float oX, float oY) {
        if(this.hellFlame){
            this.rotation -= Math.PI * Gdx.graphics.getDeltaTime();
            this.hellFlameTimer -= Gdx.graphics.getDeltaTime();
            if(this.hellFlameTimer <= 0){
                this.hellFlame = false;
                this.rotation = 0.0f;
                this.hellFlameTimer = 2.0f;
            }
        }

      this.x =  600.0f * Settings.scale * (float)Math.cos( this.rotation+ ((2 - this.index) *Math.PI / 3 ));
      this.y =  600.0f * Settings.scale * (float)Math.sin( this.rotation + ((2 - this.index) *Math.PI / 3 ));

     if (!this.hidden) {
       if (this.activated) {
         this.activateTimer -= Gdx.graphics.getDeltaTime();
         if (this.activateTimer < 0.0F) {
           if (!this.playedSfx) {
             this.playedSfx = true;
               CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitGhostIgniteEffect(this.x + oX, this.y + oY, this.scale));

               if (MathUtils.randomBoolean()) {
                    CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
               } else {
                    CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", 0.3F);
               }
           }
           this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);
           this.effect.update();
           this.effect.update();
           this.particleTimer -= Gdx.graphics.getDeltaTime();
           if (this.particleTimer < 0.0F) {
               CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitGhostlyFireEffect(this.x + oX + this.effect.y * 2.0F, this.y + oY + this.effect.y * 2.0F, this.scale));
             
             this.particleTimer = 0.12F/(float)CharacterSelectScreenPatches.characters[2].portraitAnimationType;
           } 
         } 
       } else {
         this.effect.update();
         this.particleTimer -= Gdx.graphics.getDeltaTime();
         if (this.particleTimer < 0.0F) {
             CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitGhostlyWeakFireEffect(this.x + oX + this.effect.y * 2.0F, this.y + oY + this.effect.y * 2.0F, this.scale));

           this.particleTimer = 0.12F/(float)CharacterSelectScreenPatches.characters[2].portraitAnimationType;
         } 
       } 
     } else {
       this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.0F);
     } 
   }
 }


