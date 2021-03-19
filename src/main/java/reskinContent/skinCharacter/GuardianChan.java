 package reskinContent.skinCharacter;

 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.patches.CharacterSelectScreenPatches;
 import reskinContent.reskinContent;
 import reskinContent.vfx.PortraitWhirlwindEffect;

 public  class GuardianChan extends AbstractSkinCharacter {
     private static float guardianSFX_timer = 0.0f;
     private static boolean guardianWhirl_played = false;

    public GuardianChan() {
        this.original_IMG = ImageMaster.loadImage(GuardianMod.getResourcePath("charSelect/portrait.png"));
        this.portrait_waifu =  ImageMaster.loadImage(reskinContent.assetPath("img/GuardianMod/portrait_waifu.png"));
        this.portrait_waifu2 =  ImageMaster.loadImage(reskinContent.assetPath("img/GuardianMod/portrait_waifu2.png"));

        this.ID = CardCrawlGame.languagePack.getCharacterString("Guardian").NAMES[0];
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinGuardian").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/GuardianMod/animation/GuardianChan_portrait");
    }

     @Override
     public void InitializeStaticPortraitVar() {
         guardianSFX_timer = 0.0f;
         guardianWhirl_played = false;
     }

     @Override
     public void setPos() {

         portraitSkeleton.setPosition(1092.0f * Settings.scale, Settings.HEIGHT - 1032.0f * Settings.scale);
     }

     @Override
     public void update() {
         if (reskinCount == 1 && portraitAnimationType != 0) {
             guardianSFX_timer += Gdx.graphics.getDeltaTime();
             if (guardianSFX_timer > 1.95f && !guardianWhirl_played) {
                CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitWhirlwindEffect(new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.5F, 0.55F), 1.0F), false));
                 guardianWhirl_played = true;
             }

             if (guardianSFX_timer > 12.0f) {
                 CardCrawlGame.sound.playA("MONSTER_GUARDIAN_DESTROY", MathUtils.random(-0.1F, 0.1F));
                 guardianSFX_timer = guardianSFX_timer % 1;
                 guardianWhirl_played = false;

             }
         }
     }
 }


