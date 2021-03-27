 package reskinContent.skinCharacter.skins.Slimebound;

 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.math.MathUtils;
 import com.esotericsoftware.spine.*;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import guardian.patches.GuardianEnum;
 import reskinContent.patches.CharacterSelectScreenPatches;
 import reskinContent.reskinContent;
 import reskinContent.skinCharacter.AbstractSkin;
 import reskinContent.vfx.ReskinUnlockedTextEffect;
 import reskinContent.vfx.SlimedScreenEffect;
 import slimebound.SlimeboundMod;
 import slimebound.patches.SlimeboundEnum;

 import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

 public  class Slaifu extends AbstractSkin {
     public static TextureAtlas sneckoAtlas = null;
     public static Skeleton sneckoSkeleton;
     public static AnimationState sneckoState;
     public static AnimationStateData sneckoStateData;
     public static SkeletonData sneckoData;

     private static float slime_timer = 0.0f;
     private static boolean slimeScreen = false;
     private static boolean slimySFX = false;
     private static boolean slimeCrashSFX = false;
     private static boolean slimeHitSFX = false;

    public Slaifu() {
        this.portraitStatic_IMG =  ImageMaster.loadImage(reskinContent.assetPath("img/Slimebound/Slaifu/portrait_waifu.png"));
        this.portraitAnimation_IMG =  ImageMaster.loadImage(reskinContent.assetPath("img/Slimebound/Slaifu/portrait_waifu2.png"));

        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinSlime").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/Slimebound/Slaifu/animation/SlimeBoss_portrait");
    }


     @Override
     public void loadAnimation() {
         super.loadAnimation();
         sneckoAtlas = new TextureAtlas(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/SSSSnecko/animation/Snecko_portrait_effect.atlas")));
         SkeletonJson sneckoJson = new SkeletonJson(sneckoAtlas);
         sneckoJson.setScale(Settings.scale / 1.0F);
         sneckoData = sneckoJson.readSkeletonData(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/SSSSnecko/animation/Snecko_portrait_effect.json")));


         sneckoSkeleton = new Skeleton(sneckoData);
         sneckoSkeleton.setColor(Color.WHITE);
         sneckoStateData = new AnimationStateData(sneckoData);
         sneckoState = new AnimationState(sneckoStateData);
         sneckoStateData.setDefaultMix(0.2F);

         sneckoState.setTimeScale(1.0f);
     }

     @Override
     public void setAnimation() {
         portraitState.addAnimation(0, "idle", true, 0.0f);
         portraitState.addAnimation(1, "layout", true, 0.0f);

         sneckoState.addAnimation(0, "slime_layout_effect", true, 0.0f);
     }

     @Override
     public void InitializeStaticPortraitVar() {
         slime_timer = 0.0f;
         slimeScreen = false;
         slimySFX = false;
         slimeCrashSFX = false;
         slimeHitSFX = false;
     }

     @Override
     public void setPos() {
         portraitSkeleton.setPosition(942.0f * Settings.scale, Settings.HEIGHT - 1042.0f * Settings.scale);
     }

     @Override
     public void skeletonRenderUpdate(SpriteBatch sb) {
         sneckoState.update(Gdx.graphics.getDeltaTime());
         sneckoState.apply(sneckoSkeleton);
         sneckoSkeleton.updateWorldTransform();

         sneckoSkeleton.setPosition(1296.0f * Settings.scale, Settings.HEIGHT - 1276.0f * Settings.scale);

         sneckoSkeleton.setColor(Color.WHITE.cpy());
         sneckoSkeleton.setFlip(false, false);
     }

     @Override
     public void skeletonRender(SpriteBatch sb) {
         sr.draw(CardCrawlGame.psb, portraitSkeleton);
         sr.draw(CardCrawlGame.psb, sneckoSkeleton);

         CardCrawlGame.psb.end();
         sb.begin();
     }

     @Override
     public void update() {
         if (portraitAnimationType != 0) {
             slime_timer += Gdx.graphics.getDeltaTime();
             if (slime_timer > 1.0f && !slimeScreen) {
                 CharacterSelectScreenPatches.char_effectsQueue.add(new SlimedScreenEffect());
                 slimeScreen = true;
             }

             if (slime_timer > 1.4f && !slimySFX) {
                 CardCrawlGame.sound.play("MONSTER_SLIME_ATTACK");
                 slimySFX = true;
             }

             if (slime_timer > 2.5f && !slimeCrashSFX) {
                 int roll = MathUtils.random(1);
                 if (roll == 0) {
                     CardCrawlGame.sound.play("VO_SLIMEBOSS_1A");
                 } else {
                     CardCrawlGame.sound.play("VO_SLIMEBOSS_1B");
                 }
                 slimeCrashSFX = true;
             }

             if (slime_timer > 5.5f && !slimeHitSFX) {
                 CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.5F);
                 slimeHitSFX = true;
             }

             if (slime_timer > 7.0f) {
                 slime_timer = slime_timer % 1;

                 slimeScreen = false;
                 slimySFX = false;
                 slimeCrashSFX = false;
                 slimeHitSFX = false;
             }
         }
     }
 }


