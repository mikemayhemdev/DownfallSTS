package reskinContent.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.math.Interpolation;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.FontHelper;
 import com.megacrit.cardcrawl.localization.UIStrings;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ReskinUnlockedTextEffect extends AbstractGameEffect {
   private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BetaArtUnlockEffect");
   public static final String[] TEXT = uiStrings.TEXT;
   private static final float TEXT_DURATION = 3.0F;
   private static final float START_Y = Settings.HEIGHT - 410.0F * Settings.scale;
   private static final float TARGET_Y = Settings.HEIGHT - 310.0F * Settings.scale;
   private float y;
   private String msg;
   
   public ReskinUnlockedTextEffect(int unlockValue) {
     CardCrawlGame.sound.play("UNLOCK_PING");
     this.duration = 3.0F;
     this.startingDuration = 3.0F;
     this.y = START_Y;
     this.color = Settings.GREEN_TEXT_COLOR.cpy();
     
     switch (unlockValue) {
       case 0:
         this.msg = TEXT[0];
         return;
       case 1:
         this.msg = TEXT[1];
         return;
       case 2:
         this.msg = TEXT[2];
         return;
       case 3:
         this.msg = TEXT[3];
         return;
     } 
     this.msg = "ERROR";
   }
 
 
 
   
   public void update() {
     this.duration -= Gdx.graphics.getDeltaTime();
     if (this.duration < 0.0F) {
       this.isDone = true;
       this.duration = 0.0F;
     } 
     
     if (this.duration > 2.5F) {
       this.y = Interpolation.elasticIn.apply(TARGET_Y, START_Y, (this.duration - 2.5F) * 2.0F);
       this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 2.5F) * 2.0F);
     } else if (this.duration < 0.5F) {
       this.color.a = Interpolation.pow2In.apply(0.0F, 1.0F, this.duration * 2.0F);
     } 
   }
 
 
   
   public void render(SpriteBatch sb) { FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.msg, Settings.WIDTH / 2.0F, this.y, this.color); }
   
   public void dispose() {}
 }


