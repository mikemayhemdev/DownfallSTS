package reskinContent.patches;


import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import guardian.patches.GuardianEnum;
import reskinContent.reskinContent;

import reskinContent.helpers.PortraitHexaghostOrb;
import reskinContent.vfx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import guardian.GuardianMod;

import slimebound.SlimeboundMod;
import slimebound.patches.SlimeboundEnum;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;


@SuppressWarnings("unused")
public class CharacterSelectScreenPatches
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(reskinContent.makeID("ReSkin"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static int reskinCount = 0;

    public static Hitbox reskinRight;
    public static Hitbox reskinLeft;
    public static Hitbox portraitAnimationLeft;
    public static Hitbox portraitAnimationRight;

    private static float reskin_Text_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float reskin_W = reskin_Text_W + 200.0f * Settings.scale;
    private static float reskinX_center = 600.0F * Settings.scale;

    public static Field charInfoField;

//    Guardian var
    private static float guardianSFX_timer = 0.0f;
    private static boolean guardianWhirl_played = false;


//    slime var
    private static float slime_timer = 0.0f;
    private static boolean slimeScreen = false;
    private static boolean slimySFX = false;
    private static boolean slimeCrashSFX  = false;
    private static boolean slimeHitSFX  = false;


//    Hexaghost var
    private static ArrayList<PortraitHexaghostOrb> orbs = new ArrayList();
    private static final String ACTIVATE_STATE = "Activate";
    private static final String ACTIVATE_ORB = "Activate Orb";
    private static final String DEACTIVATE_ALL_ORBS = "Deactivate";
    private static int orbActiveCount = 0;

    private static final Float ghostFireTimer_time = 1.0f;
    private static final Float giantGhostFireTimer_time = 3.0f;
    private static final Float ghostFireDeactivate_time = 0.5f;
    private static float ghostFireTimer = ghostFireTimer_time;
    private static boolean giantGhostFire = false;
    private static float giantGhostFireTimer = giantGhostFireTimer_time;

    public static Hitbox hexaghostMask;
    public static boolean hexaghostMask_switch = true;//true for take on

    public static ArrayList<AbstractGameEffect> char_effectsQueue = new ArrayList();;
    public static ArrayList<AbstractGameEffect> char_effectsQueue_toRemove = new ArrayList();;

//  Snecko var
    private static boolean confuseUsed = false;
    private static boolean confuseSFXUsed = false;
    private static boolean debuffSFXUsed = false;
    private static boolean waifuAppar = false;
    private static float sneckoTimer = 0.0f;
    private static float sneckoAfterImageTimer = 0.0f;

    private static Color halfWhite = Color.WHITE.cpy();
    private static float sneckoWaifuX = 0.0f;
    private static float sneckoWaifuY = 0.0f;


//    portrait img
//2 is for animated portrait

//The Guardian
    private static Texture GuardianOriginal =  ImageMaster.loadImage(GuardianMod.getResourcePath("charSelect/portrait.png"));
    private static Texture GuardianChan =  ImageMaster.loadImage(reskinContent.assetPath("img/GuardianMod/portrait_waifu.png"));
    private static Texture GuardianChan2 =  ImageMaster.loadImage(reskinContent.assetPath("img/GuardianMod/portrait_waifu2.png"));

//Slime Boss
    private static Texture SlimeOriginal =  ImageMaster.loadImage(SlimeboundMod.getResourcePath("charSelect/portrait.png"));
    private static Texture SlaifuTexture =  ImageMaster.loadImage(reskinContent.assetPath("img/Slimebound/portrait_waifu.png"));
    private static Texture SlaifuTexture2 =  ImageMaster.loadImage(reskinContent.assetPath("img/Slimebound/portrait_waifu2.png"));

//Hexaghost
    private static Texture hexaghostOriginal =  ImageMaster.loadImage("hexamodResources/images/charSelect/charBG.png");
    private static Texture hexaghostTexture =  ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/portrait_waifu.png"));
    private static Texture hexaghostTexture2 =  ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/portrait_waifu2.png"));
    private static Texture hexaghostTextureMask =  ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/portrait_waifu_m.png"));

//Snecko
    private static Texture sneckoOriginal =  ImageMaster.loadImage("sneckomodResources/images/charSelect/portrait.png");
    private static Texture sneckoTexture =  ImageMaster.loadImage(reskinContent.assetPath("img/SneckoMod/portrait_waifu.png"));
    private static Texture sneckoTexture2 =  ImageMaster.loadImage(reskinContent.assetPath("img/SneckoMod/portrait_waifu2.png"));


//portrait Skeleton
    public static TextureAtlas portraitAtlas = null ;
    public static Skeleton portraitSkeleton;
    public static AnimationState portraitState ;
    public static AnimationStateData portraitStateData;
    public static SkeletonData portraitData;

    public static TextureAtlas sneckoAtlas = null ;
    public static Skeleton sneckoSkeleton;
    public static AnimationState sneckoState ;
    public static AnimationStateData sneckoStateData;
    public static SkeletonData sneckoData;

    private static Map<Integer, String> characterOptionNames;
    private static Map<Integer, String> portraitAtlasMaps;
    private static Map<Integer, Boolean> characterUnlocksMaps;

    static{
        characterOptionNames = new HashMap<>();
        portraitAtlasMaps = new HashMap<>();
        characterUnlocksMaps = new HashMap<>();

        characterOptionNames.put( 0, CardCrawlGame.languagePack.getCharacterString("Guardian").NAMES[0]  );
        characterOptionNames.put( 1, CardCrawlGame.languagePack.getCharacterString("Slimebound").NAMES[0] );
        characterOptionNames.put( 2, CardCrawlGame.languagePack.getCharacterString("hexamod:theHexaghost").NAMES[0] );
        characterOptionNames.put( 3, CardCrawlGame.languagePack.getCharacterString("sneckomod:theSnecko").NAMES[0] );


        portraitAtlasMaps.put( 0 ,reskinContent.assetPath("img/GuardianMod/animation/GuardianChan_portrait"));
        portraitAtlasMaps.put( 1 ,reskinContent.assetPath("img/Slimebound/animation/SlimeBoss_portrait"));
        portraitAtlasMaps.put( 2 ,reskinContent.assetPath("img/HexaghostMod/animation/Hexaghost_portrait"));
        portraitAtlasMaps.put( 3 ,reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait"));

        characterUnlocksMaps.put( 0 , reskinContent.guardianReskinUnlock);
        characterUnlocksMaps.put( 1 , reskinContent.slimeReskinUnlock);
        characterUnlocksMaps.put( 2 , reskinContent.hexaghostReskinUnlock);
        characterUnlocksMaps.put( 3 , reskinContent.sneckoReskinUnlock);
    }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            // Called when you first open the screen, create hitbox for each button

            char_effectsQueue.clear();

            reskinRight = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            reskinLeft = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            portraitAnimationLeft = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            portraitAnimationRight = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);


            reskinRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center , 800.0F * Settings.scale);
            reskinLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center , 800.0F * Settings.scale);
            portraitAnimationLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center , 920.0F * Settings.scale);
            portraitAnimationRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center ,  920.0F * Settings.scale);

            hexaghostMask = new Hitbox(250.0f * Settings.scale, 350.0f * Settings.scale);
            hexaghostMask.move(1350.0F * Settings.scale ,  780.0F * Settings.scale);



            if(reskinContent.portraitAnimationType != 0 && reskinCount != 0){
                loadPortraitAnimation(0);
                System.out.println("立绘载入1");
                orbs.clear();
            }
        }
    }


//载入动态立绘
    private static void loadPortraitAnimation(Integer characterCount) {
        if(reskinContent.portraitAnimationType != 0){
        portraitAtlas = new TextureAtlas(Gdx.files.internal(portraitAtlasMaps.get(characterCount)) + ".atlas");
        SkeletonJson json = new SkeletonJson(portraitAtlas);
        json.setScale(Settings.scale / 1.0F);
        portraitData = json.readSkeletonData(Gdx.files.internal(portraitAtlasMaps.get(characterCount) + ".json"));


        portraitSkeleton = new Skeleton(portraitData);
        portraitSkeleton.setColor(Color.WHITE);
        portraitStateData = new AnimationStateData(portraitData);
        portraitState = new AnimationState(portraitStateData);
        portraitStateData.setDefaultMix(0.2F);

        portraitState.setTimeScale(1.0f);



        if(characterCount == 0){
            portraitState.setAnimation(1, "fade_in", false);
            portraitState.addAnimation(0, "idle", true,0.0f);
            InitializeStaticPortraitVar();
        }

        if(characterCount == 1){
            sneckoAtlas = new TextureAtlas(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait_effect.atlas")));
            SkeletonJson sneckoJson = new SkeletonJson(sneckoAtlas);
            sneckoJson.setScale(Settings.scale / 1.0F);
            sneckoData = sneckoJson.readSkeletonData(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait_effect.json")));


            sneckoSkeleton = new Skeleton(sneckoData);
            sneckoSkeleton.setColor(Color.WHITE);
            sneckoStateData = new AnimationStateData(sneckoData);
            sneckoState = new AnimationState(sneckoStateData);
            sneckoStateData.setDefaultMix(0.2F);

            sneckoState.setTimeScale(1.0f);


            portraitState.addAnimation(0, "idle", true,0.0f);
            portraitState.addAnimation(1, "layout", true,0.0f);

            sneckoState.addAnimation(0, "slime_layout_effect", true,0.0f);
            InitializeStaticPortraitVar();
        }




        if(characterCount ==2){

            portraitState.setAnimation(1, "fade_in", false);
            portraitState.addAnimation(0, "idle_loop_Mask", true,0.0f);
            portraitState.addAnimation(2, "PlasmaRation", true,0.0f);
            portraitState.addAnimation(3, "maskHalo_fade_in", true,0.0f);
            portraitState.addAnimation(3, "maskHalo_loop", true,0.0f);

            if (reskinContent.hexaghostMask){
                hexaghostMask_switch = false;
                portraitState.addAnimation(3, "Mask_off", false,1.0f);
            }

            InitializeStaticPortraitVar();
            if(orbs.size() == 0){
                createOrbs();
                for (PortraitHexaghostOrb orb : orbs) {
                    orb.deactivate();
                }
                hexaghostChangeState(ACTIVATE_STATE);
            }}


            if(characterCount == 3){
                sneckoAtlas = new TextureAtlas(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait_effect.atlas")));
                SkeletonJson sneckoJson = new SkeletonJson(sneckoAtlas);
                sneckoJson.setScale(Settings.scale / 1.0F);
                sneckoData = sneckoJson.readSkeletonData(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait_effect.json")));


                sneckoSkeleton = new Skeleton(sneckoData);
                sneckoSkeleton.setColor(Color.WHITE);
                sneckoStateData = new AnimationStateData(sneckoData);
                sneckoState = new AnimationState(sneckoStateData);
                sneckoStateData.setDefaultMix(0.2F);

                sneckoState.setTimeScale(1.0f);


                portraitState.addAnimation(0, "layout", true,0.0f);
                sneckoState.addAnimation(0, "layout_effect", true,0.0f);
                InitializeStaticPortraitVar();
            }

        }

        else {
            InitializeStaticPortraitVar();
        }
    }
    private static void InitializeStaticPortraitVar(){
        guardianSFX_timer = 0.0f;
        guardianWhirl_played = false;

        slime_timer = 0.0f;
        slimeScreen = false;
        slimySFX = false;
        slimeCrashSFX = false;
        slimeHitSFX = false;

        ghostFireTimer = ghostFireTimer_time;
        giantGhostFireTimer =giantGhostFireTimer_time;
        orbActiveCount = 0;

        confuseUsed = false;
        confuseSFXUsed = false;
        debuffSFXUsed = false;
        waifuAppar = false;
        sneckoTimer = 0.0f;
        sneckoAfterImageTimer = 0.0f;
    }


     private static void createOrbs() {
            orbs.add(new PortraitHexaghostOrb(-90.0F, 380.0F, orbs.size()));
            orbs.add(new PortraitHexaghostOrb(90.0F, 380.0F, orbs.size()));
            orbs.add(new PortraitHexaghostOrb(160.0F, 250.0F, orbs.size()));
            orbs.add(new PortraitHexaghostOrb(90.0F, 120.0F, orbs.size()));
            orbs.add(new PortraitHexaghostOrb(-90.0F, 120.0F, orbs.size()));
            orbs.add(new PortraitHexaghostOrb(-160.0F, 250.0F, orbs.size()));
         }


       public static void hexaghostChangeState(String stateName) {
             switch (stateName) {
               case ACTIVATE_STATE:
                        for (PortraitHexaghostOrb orb : orbs) {
                               orb.activate(portraitSkeleton.getX(), portraitSkeleton.getY());
                             }
                      orbActiveCount = 6;
                         break;

               case ACTIVATE_ORB:
                        for (PortraitHexaghostOrb orb : orbs) {
                            if (!orb.activated) {
                                   orb.activate(portraitSkeleton.getX(), portraitSkeleton.getY());
                                   break;
                                  }
                            }

                       orbActiveCount++;

                         if (orbActiveCount > 6) {
                             for (PortraitHexaghostOrb orb : orbs) {
                                     orb.hellFlameActivate();
                             }

                             char_effectsQueue.add(new PortraitScreenOnFireEffect());
                              orbActiveCount = 0;
                              giantGhostFire = true;
                             ghostFireTimer = ghostFireTimer_time + ghostFireDeactivate_time;
                            } else {
                             ghostFireTimer = ghostFireTimer_time;
                         }
                        break;


              case DEACTIVATE_ALL_ORBS:
                     for (PortraitHexaghostOrb orb : orbs) {
                         orb.deactivate();
                     }
                  CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
                  CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

                     giantGhostFire = false;
                     giantGhostFireTimer = giantGhostFireTimer_time;
                     break;
                 }
          }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenPatch_Render
    {
        @SpirePostfixPatch
        public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb)
        {
            // Render your buttons/images by passing SpriteBatch


            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 3; i++){
                    InitializeReskinCount(i);

                if (o.name.equals(characterOptionNames.get(i)) && o.selected) {
                    reskinRight.render(sb);
                    reskinLeft.render(sb);
                    portraitAnimationLeft.render(sb);
                    portraitAnimationRight.render(sb);

                    if(i == 2 && reskinCount == 1)
                        hexaghostMask.render(sb);

                    if(reskinCount != 0 && characterUnlocksMaps.get(i)){
                        if (portraitAnimationLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE.cpy());} else {sb.setColor(Color.LIGHT_GRAY.cpy());}
                        sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                        if (portraitAnimationRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE.cpy());} else {sb.setColor(Color.LIGHT_GRAY.cpy());}
                        sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);

                    }

                    if(characterUnlocksMaps.get(i)){
                        if (reskinRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE.cpy());} else {sb.setColor(Color.LIGHT_GRAY.cpy());}
                        sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                        if (reskinLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE.cpy());} else {sb.setColor(Color.LIGHT_GRAY.cpy());}
                        sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                    }

                    FontHelper.cardTitleFont.getData().setScale(1.0F);
                    FontHelper.bannerFont.getData().setScale(0.8F);

                    if(reskinCount != 0 && characterUnlocksMaps.get(i)) {
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimationType")).TEXT[reskinContent.portraitAnimationType], Settings.WIDTH / 2.0F - reskinX_center, 920.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                        FontHelper.renderFontCentered(sb, FontHelper.bannerFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimation")).TEXT[0], Settings.WIDTH / 2.0F - reskinX_center, 970 * Settings.scale , Settings.GOLD_COLOR);
                    }
                    if(characterUnlocksMaps.get(i)){
                        FontHelper.renderFontCentered(sb, FontHelper.bannerFont, TEXT[0], Settings.WIDTH / 2.0F - reskinX_center, 850.0F * Settings.scale , Settings.GOLD_COLOR.cpy());
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[(reskinCount % 2) * (i + 1) + 1], Settings.WIDTH / 2.0F - reskinX_center, 800.0F * Settings.scale, Settings.GOLD_COLOR.cpy());

                    }





                }
            }
        }
    }

    }

    //                动态立绘
    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenPatch_portraitSkeleton
    {
        @SpireInsertPatch(rloc = 45)
        public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb)
        {
            // Render your buttons/images by passing SpriteBatch

            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 3; i++){
                    InitializeReskinCount(i);
                    if (o.name.equals(characterOptionNames.get(i)) && o.selected && reskinCount == 1 && reskinContent.portraitAnimationType != 0) {

                        portraitState.update(Gdx.graphics.getDeltaTime());
                        portraitState.apply(portraitSkeleton);
                        portraitSkeleton.updateWorldTransform();



                        if(i == 0)
                            portraitSkeleton.setPosition(1092.0f * Settings.scale, Settings.HEIGHT - 1032.0f * Settings.scale);   //                       立绘位置

                        if(i == 1)
                            portraitSkeleton.setPosition(942.0f * Settings.scale, Settings.HEIGHT - 1042.0f * Settings.scale);   //                       立绘位置

                        if(i == 2)
                            portraitSkeleton.setPosition(1266.0f * Settings.scale, Settings.HEIGHT - 597.0f * Settings.scale);

                        if(i == 3)
                            portraitSkeleton.setPosition(1296.0f * Settings.scale, Settings.HEIGHT - 1276.0f * Settings.scale);


                        if(i == 1 || i ==3){
                            sneckoState.update(Gdx.graphics.getDeltaTime());
                            sneckoState.apply(sneckoSkeleton);
                            sneckoSkeleton.updateWorldTransform();

                            sneckoSkeleton.setPosition(1296.0f * Settings.scale, Settings.HEIGHT - 1276.0f * Settings.scale);

                            sneckoSkeleton.setColor(Color.WHITE.cpy());
                            sneckoSkeleton.setFlip(false,false);
                        }


                        portraitSkeleton.setColor(Color.WHITE.cpy());
                        portraitSkeleton.setFlip(false,false);

                    sb.end();
                    CardCrawlGame.psb.begin();



                        if(i == 3 ) {
                            if(reskinContent.portraitAnimationType == 2 && waifuAppar){
                                sneckoWaifuX = portraitSkeleton.findBone("Waifu1_root").getX();
                                sneckoWaifuY = portraitSkeleton.findBone("Waifu1_root").getY();

                                portraitSkeleton.setColor(halfWhite);


                                portraitSkeleton.findBone("Waifu1_root").setPosition(
                                        sneckoWaifuX + 100.0f * Settings.scale * (float)Math.cos( sneckoAfterImageTimer * Math.PI) - 100.0f * Settings.scale,
                                        sneckoWaifuY + 50.0f * Settings.scale * (float)Math.sin( sneckoAfterImageTimer * Math.PI));
                                portraitSkeleton.updateWorldTransform();
                                sr.draw(CardCrawlGame.psb, portraitSkeleton);
//===========
                                portraitSkeleton.setColor(Color.WHITE.cpy());
                                portraitSkeleton.findBone("Waifu1_root").setPosition(sneckoWaifuX,sneckoWaifuY);
                                portraitSkeleton.updateWorldTransform();
                                sr.draw(CardCrawlGame.psb, portraitSkeleton);
//=============

                                portraitSkeleton.setColor(halfWhite);
                                portraitSkeleton.findBone("Waifu1_root").setPosition(
                                        sneckoWaifuX + 100.0f * Settings.scale * (float)Math.cos( (sneckoAfterImageTimer + 1.0f) * Math.PI) + 100.0f * Settings.scale,
                                        sneckoWaifuY + 50.0f * Settings.scale * (float)Math.sin( (sneckoAfterImageTimer + 1.0f) * Math.PI));
                                portraitSkeleton.updateWorldTransform();
                                sr.draw(CardCrawlGame.psb, portraitSkeleton);

                            }
                            else {
                                 sr.draw(CardCrawlGame.psb, portraitSkeleton);
                              }

                            sr.draw(CardCrawlGame.psb, sneckoSkeleton);
                        }else {
                          sr.draw(CardCrawlGame.psb, portraitSkeleton);
                        }

                        if(i == 1){
                            sr.draw(CardCrawlGame.psb, sneckoSkeleton);
                        }

                        CardCrawlGame.psb.end();
                    sb.begin();



// render specific effect
                        if(char_effectsQueue.size() > 0){
                            for(int k =0;k < char_effectsQueue.size(); k++){
                                if(!char_effectsQueue.get(k).isDone){
                                    char_effectsQueue.get(k).render(sb);
                                    char_effectsQueue.get(k).update();
                                }else {
                                    if(char_effectsQueue_toRemove == null)
                                        char_effectsQueue_toRemove = new ArrayList<>();
                                    if(!char_effectsQueue_toRemove.contains(char_effectsQueue.get(k)))
                                        char_effectsQueue_toRemove.add(char_effectsQueue.get(k));
                                }
                            }
//  dispose
                            if(char_effectsQueue_toRemove != null)
                                char_effectsQueue.removeAll(char_effectsQueue_toRemove);
                        }

                    }} }}}

//    立绘动画重置

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")

    public static class CharacterOptionPatch_reloadAnimation
    {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance)
        {
            char_effectsQueue.clear();

            for(int i = 0; i <= 3; i++){
                InitializeReskinCount(i);

            if(__instance.name.equals(characterOptionNames.get(i)) && reskinContent.portraitAnimationType != 0){
                orbs.clear();

                if(reskinCount != 0)
                 loadPortraitAnimation(i);
                System.out.println("立绘载入2");

            }

     }}}


    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {


// deal with click box and portrait img
            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 3; i++){
                    InitializeReskinCount(i);
                if(characterUnlocksMaps.get(i)){
                if (o.name.equals(characterOptionNames.get(i)) && o.selected) {
                    __instance.bgCharImg = updateBgImg(i);

                    if (InputHelper.justClickedLeft && reskinLeft.hovered) {
                        reskinLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                    }

                    if (InputHelper.justClickedLeft && reskinRight.hovered) {
                        reskinRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1"); }

                    if (InputHelper.justClickedLeft && portraitAnimationLeft.hovered && reskinCount != 0) {
                        portraitAnimationLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1"); }

                    if (InputHelper.justClickedLeft && portraitAnimationRight.hovered && reskinCount != 0) {
                        portraitAnimationRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1"); }

                    if (reskinLeft.justHovered || reskinRight.justHovered )
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

                    if ( (portraitAnimationLeft.justHovered || portraitAnimationRight.justHovered) && reskinCount != 0)
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

                    if (InputHelper.justClickedLeft && hexaghostMask.hovered && i == 2 && reskinCount != 0)
                        hexaghostMask.clickStarted = true;

//==================================


                        if(reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            reskinRight.clicked = false;
                            orbs.clear();
                            char_effectsQueue.clear();

                            if (reskinCount < 1) {
                                reskinCount += 1;
                            } else {
                                reskinCount = 0;
                            }
                            loadPortraitAnimation(i);
                            __instance.bgCharImg = updateBgImg(i);
                        }

                        if(reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            reskinLeft.clicked = false;
                            orbs.clear();
                            char_effectsQueue.clear();

                            if (reskinCount > 0) {
                                reskinCount -= 1;
                            } else {
                                reskinCount = 1;
                            }
                            loadPortraitAnimation(i);
                            __instance.bgCharImg = updateBgImg(i);
                        }


                        if(portraitAnimationLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            portraitAnimationLeft.clicked = false;
                            orbs.clear();
                            char_effectsQueue.clear();
                            if(reskinContent.portraitAnimationType <= 0){
                                reskinContent.portraitAnimationType = 2;
                            }else {
                                reskinContent.portraitAnimationType -= 1;
                            }
                            loadPortraitAnimation(i);
                            __instance.bgCharImg = updateBgImg(i);
                        }

                        if(portraitAnimationRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            portraitAnimationRight.clicked = false;
                            orbs.clear();
                            char_effectsQueue.clear();
                            if(reskinContent.portraitAnimationType >= 2){
                                reskinContent.portraitAnimationType = 0;
                            }else {
                                reskinContent.portraitAnimationType += 1;
                            }
                            loadPortraitAnimation(i);
                            __instance.bgCharImg = updateBgImg(i);
                        }

                    if(hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){

                        __instance.bgCharImg = updateBgImg(i);
                    }



                    InitializeReskinCount(i);
//============================
// 蹲酱
                    if(i == 0 && reskinCount == 1 && reskinContent.portraitAnimationType != 0){
                        guardianSFX_timer += Gdx.graphics.getDeltaTime();
                        if( guardianSFX_timer > 1.95f && !guardianWhirl_played){
                            char_effectsQueue.add(new PortraitWhirlwindEffect(new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.5F, 0.55F),1.0F),false));
                            guardianWhirl_played = true;
                        }

                        if( guardianSFX_timer > 12.0f){
                            CardCrawlGame.sound.playA("MONSTER_GUARDIAN_DESTROY", MathUtils.random(-0.1F, 0.1F));
                            guardianSFX_timer = guardianSFX_timer % 1;
                            guardianWhirl_played = false;

                        }
                    }


//   史莱姆
                    if(i == 1 && reskinCount == 1 && reskinContent.portraitAnimationType != 0){
                        slime_timer += Gdx.graphics.getDeltaTime();
                        if( slime_timer > 1.0f && !slimeScreen){
                            char_effectsQueue.add(new SlimedScreenEffect());
                            slimeScreen = true;
                        }

                        if(slime_timer >1.4f && !slimySFX){
                            CardCrawlGame.sound.play("MONSTER_SLIME_ATTACK");
                            slimySFX = true;
                        }

                        if(slime_timer > 2.5f && !slimeCrashSFX){
                            int roll = MathUtils.random(1);
                                if (roll == 0) {
                                    CardCrawlGame.sound.play("VO_SLIMEBOSS_1A");
                                     } else {
                                    CardCrawlGame.sound.play("VO_SLIMEBOSS_1B");
                                     }
                                slimeCrashSFX = true;
                        }

                        if(slime_timer > 5.5f && !slimeHitSFX){
                            CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.5F);
                            slimeHitSFX = true;
                        }

                        if( slime_timer > 7.0f){
                            slime_timer = slime_timer % 1;

                            slimeScreen = false;
                            slimySFX = false;
                            slimeCrashSFX = false;
                            slimeHitSFX = false;
                        }
                    }




//-----------------------
//六火

//面具互动

                    if(i == 2 && reskinCount == 1) {
                        if(reskinContent.portraitAnimationType != 0){
                        if (hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            hexaghostMask.clicked = false;

                            if (hexaghostMask_switch) {
                                portraitState.setAnimation(3, "Mask_off", false);
                                hexaghostMask_switch = false;
                                reskinContent.hexaghostMask = true;
                                reskinContent.saveSettings();
                            } else {
                                portraitState.setAnimation(3, "Mask_on", false);
                                portraitState.addAnimation(3, "maskHalo_loop", true, 0.0f);
                                hexaghostMask_switch = true;
                                reskinContent.hexaghostMask = false;
                                reskinContent.saveSettings();
                            }
                        }

//  地狱火相关
                        if(orbs.size() != 0){
                            if(!giantGhostFire){
                                ghostFireTimer -= Gdx.graphics.getDeltaTime();
                                if(ghostFireTimer < 0){
                                    hexaghostChangeState(ACTIVATE_ORB);
                                }
                            }else {
                                giantGhostFireTimer -= Gdx.graphics.getDeltaTime();
                                if(giantGhostFireTimer <= 0){
                                    hexaghostChangeState(DEACTIVATE_ALL_ORBS);
                                }}
                        }

// 鬼火更新
                        for (PortraitHexaghostOrb orb : orbs) {
                            orb.update(portraitSkeleton.getX(), portraitSkeleton.getY());
                        }
//面具hitbox
                        hexaghostMask.move(
                                portraitSkeleton.getX()+portraitSkeleton.findBone("Mask").getWorldX(),
                                portraitSkeleton.getY()+portraitSkeleton.findBone("Mask").getWorldY()
                        );
                    }else {
                            if (hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                                hexaghostMask.clicked = false;

                                if (hexaghostMask_switch) {
                                    hexaghostMask_switch = false;
                                    reskinContent.hexaghostMask = true;
                                    reskinContent.saveSettings();
                                } else {
                                    hexaghostMask_switch = true;
                                    reskinContent.hexaghostMask = false;
                                    reskinContent.saveSettings();
                                }
                            }
                        }
                    }



//   异蛇相关
                    if(i == 3 && reskinCount == 1 && reskinContent.portraitAnimationType != 0) {
                        sneckoTimer += Gdx.graphics.getDeltaTime();
                        sneckoAfterImageTimer += Gdx.graphics.getDeltaTime();

                        if(!confuseUsed && sneckoTimer > 1.0f){

                            CardCrawlGame.sound.play("MONSTER_SNECKO_GLARE");
                            char_effectsQueue.add(new PortraitIntimidateEffect(
                                    portraitSkeleton.getX()+portraitSkeleton.findBone("Snecko_eyeBall_R").getWorldX(),
                                    portraitSkeleton.getY()+portraitSkeleton.findBone("Snecko_eyeBall_R").getWorldY()
                            ));
                            char_effectsQueue.add(new PortraitIntimidateEffect(
                                    portraitSkeleton.getX()+portraitSkeleton.findBone("Snecko_eyeBall_L").getWorldX(),
                                    portraitSkeleton.getY()+portraitSkeleton.findBone("Snecko_eyeBall_L").getWorldY()
                            ));

                            confuseUsed = true;
                        }

                        if(!confuseSFXUsed && sneckoTimer > 2.5f){
                            CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
                            confuseSFXUsed = true;
                        }

                        if(!debuffSFXUsed && sneckoTimer > 7.5f){
                                  int roll = MathUtils.random(0, 2);
                                  if (roll == 0) {
                                        CardCrawlGame.sound.play("DEBUFF_1");
                                       } else if (roll == 1) {
                                         CardCrawlGame.sound.play("DEBUFF_2");
                                      } else {
                                        CardCrawlGame.sound.play("DEBUFF_3");
                                     }

                            CardCrawlGame.sound.play("BLOCK_BREAK");
                            debuffSFXUsed = true;
                        }


                        if(sneckoTimer > 3.1f )     waifuAppar = true;
                        if(sneckoTimer > 6.95f )     waifuAppar = false;

                        if(sneckoAfterImageTimer > 2.0f)
                            sneckoAfterImageTimer = 0.0f;
                        halfWhite.a = 0.2f + 0.3f * sneckoAfterImageTimer;

                        if(sneckoTimer > 8.0f){
                            sneckoTimer = sneckoTimer % 1 ;
                            confuseUsed = false;
                            confuseSFXUsed = false;
                            debuffSFXUsed = false;

                        }
                    }



// hitbox update


                    reskinLeft.update();
                    reskinRight.update();

                    if(reskinCount != 0){
                        portraitAnimationLeft.update();
                        portraitAnimationRight.update();
                    }

                    hexaghostMask.update();

                }}}
            }}}





    private static void InitializeReskinCount(int characterCount){
        switch (characterCount){
            case 0 :
                reflashReskinCount(reskinContent.guardianReskinAnimation);
                break;
            case 1:
                reflashReskinCount(reskinContent.slimeReskinAnimation);
                break;
            case 2:
                reflashReskinCount(reskinContent.hexaghostReskinAnimation);
                break;
            case 3:
                reflashReskinCount(reskinContent.sneckoReskinAnimation);
                break;
            default:
                break;
        }

        if (!(reskinCount == 0 || reskinCount == 1 ))
            reskinCount = 0;
    }



    public static void reflashReskinCount(boolean reskinAnimation){
        if(reskinAnimation){
            if(reskinCount != 1)
                reskinCount = 1;
        }else {
            if(reskinCount != 0)
                reskinCount = 0;
        }
    }

    public static Texture updateBgImg(int selectedCharCount){

        switch(selectedCharCount){
           case 0:
            switch (reskinCount){
                case 0:
                    reskinContent.guardianReskinAnimation = false;
                    reskinContent.saveSettings();
                    return GuardianOriginal;
                case 1:
                    reskinContent.guardianReskinAnimation = true;
                    reskinContent.saveSettings();
                    if(reskinContent.portraitAnimationType == 0){
                        return GuardianChan;
                    }else {
                        return GuardianChan2;
                    }
                default:
                    return GuardianOriginal;
            }

            case 1:
            switch (reskinCount ){
                case 0:
                    reskinContent.slimeReskinAnimation = false;
                    reskinContent.saveSettings();
                    return SlimeOriginal;
                case 1:
                    reskinContent.slimeReskinAnimation = true;
                    reskinContent.saveSettings();
                    if(reskinContent.portraitAnimationType == 0){
                        return SlaifuTexture;
                    }else {
                        return SlaifuTexture2;
                    }
                default:
                    return SlimeOriginal;
            }


            case 2:
            switch (reskinCount ){
                case 0:
                    reskinContent.hexaghostReskinAnimation = false;
                    reskinContent.saveSettings();
                    return hexaghostOriginal;
                case 1:
                    reskinContent.hexaghostReskinAnimation = true;
                    reskinContent.saveSettings();
                    if(reskinContent.portraitAnimationType == 0){
                        if(hexaghostMask_switch){
                            return hexaghostTextureMask;
                        }else {
                            return hexaghostTexture;
                        }

                    }else {
                        return hexaghostTexture2;
                    }
                default:
                    return hexaghostOriginal;
            }



            case 3:
            switch (reskinCount ){
                case 0:
                    reskinContent.sneckoReskinAnimation = false;
                    reskinContent.saveSettings();
                    return sneckoOriginal;
                case 1:
                    reskinContent.sneckoReskinAnimation = true;
                    reskinContent.saveSettings();
                    if(reskinContent.portraitAnimationType == 0){
                        return sneckoTexture;
                    }else {
                        return sneckoTexture2;
                    }
                default:
                    return sneckoOriginal;
            }



        default:
            return null;

        } }
}