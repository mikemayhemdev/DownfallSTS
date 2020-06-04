package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;

import TheGuardianChan.helpers.PortraitHexaghostOrb;
import TheGuardianChan.vfx.PortraitScreenOnFireEffect;
import TheGuardianChan.vfx.PortraitWhirlwindEffect;
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
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import guardian.GuardianMod;

import slimebound.SlimeboundMod;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static TheGuardianChan.TheGuardianChan.disablePortraitAnimation;
import static com.megacrit.cardcrawl.core.AbstractCreature.sr;


@SuppressWarnings("unused")
public class CharacterSelectScreenPatches
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("ReSkin"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static int reskinCount = 0;

    public static Hitbox reskinRight;
    public static Hitbox reskinLeft;
    public static Hitbox portraitAnimationLeft;
    public static Hitbox portraitAnimationRight;

    public static int disablePortraitAnimationVar = 1;

    private static float reskin_Text_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float reskin_W = reskin_Text_W + 200.0f * Settings.scale;
    private static float reskinX_center = 600.0F * Settings.scale;

    public static Field charInfoField;

//    Guardian var
    private static float guardianSFX_timer = 12.0f;
    private static boolean guardianWhirl_played = false;

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




//    portrait img
//2 is for animated portrait

//The Guardian
    private static Texture GuardianOriginal =  ImageMaster.loadImage(GuardianMod.getResourcePath("charSelect/portrait.png"));
    private static Texture GuardianChan =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/GuardianMod/portrait_waifu.png"));
    private static Texture GuardianChan2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/GuardianMod/portrait_waifu2.png"));

//Slime Boss
    private static Texture SlimeOriginal =  ImageMaster.loadImage(SlimeboundMod.getResourcePath("charSelect/portrait.png"));
    private static Texture SlaifuTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/Slimebound/portrait_waifu.png"));
    private static Texture SlaifuTexture2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/Slimebound/portrait_waifu2.png"));

//Hexaghost
    private static Texture hexaghostOriginal =  ImageMaster.loadImage("hexamodResources/images/charSelect/charBG.png");
    private static Texture hexaghostTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/HexaghostMod/portrait_waifu.png"));
    private static Texture hexaghostTexture2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/HexaghostMod/portrait_waifu2.png"));
    private static Texture hexaghostTextureMask =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/HexaghostMod/portrait_waifu_m.png"));

//Snecko
    private static Texture sneckoOriginal =  ImageMaster.loadImage("sneckomodResources/images/charSelect/portrait.png");
    private static Texture sneckoTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/SneckoMod/portrait_waifu.png"));
    private static Texture sneckoTexture2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/SneckoMod/portrait_waifu2.png"));


//portrait Skeleton
    public static TextureAtlas portraitAtlas = null ;
    public static Skeleton portraitSkeleton;
    public static AnimationState portraitState ;
    public static AnimationStateData portraitStateData;
    public static SkeletonData portraitData;

    private static Map<Integer, String> characterOptionNames;
    private static Map<Integer, String> portraitAtlasMaps;


    static{
        characterOptionNames = new HashMap<>();
        portraitAtlasMaps = new HashMap<>();

        characterOptionNames.put( 0, CardCrawlGame.languagePack.getCharacterString("Guardian").NAMES[0]  );
        characterOptionNames.put( 1, CardCrawlGame.languagePack.getCharacterString("Slimebound").NAMES[0] );
        characterOptionNames.put( 2, CardCrawlGame.languagePack.getCharacterString("hexamod:theHexaghost").NAMES[0] );
        characterOptionNames.put( 3, CardCrawlGame.languagePack.getCharacterString("sneckomod:theSnecko").NAMES[0] );


        portraitAtlasMaps.put( 0 ,TheGuardianChan.assetPath("img/GuardianMod/animation/GuardianChan_portrait"));
        portraitAtlasMaps.put( 1 ,TheGuardianChan.assetPath("img/GuardianMod/animation/GuardianChan_portrait"));
        portraitAtlasMaps.put( 2 ,TheGuardianChan.assetPath("img/HexaghostMod/animation/Hexaghost_portrait"));
        portraitAtlasMaps.put( 3 ,TheGuardianChan.assetPath("img/GuardianMod/animation/GuardianChan_portrait"));

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

            if(!disablePortraitAnimation && reskinCount != 0){
                loadPortraitAnimation(0);
                System.out.println("立绘载入1");
                orbs.clear();
            }
        }
    }


//载入动态立绘
    private static void loadPortraitAnimation(Integer characterCount) {
        if(!disablePortraitAnimation){
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


        if(characterCount !=2){
            portraitState.setAnimation(1, "fade_in", false);
            portraitState.addAnimation(0, "idle", true,0.0f);
            InitializeStaticPortraitVar();
        }


        if(characterCount ==2){

            portraitState.setAnimation(1, "fade_in", false);
            portraitState.addAnimation(0, "idle_loop_Mask", true,0.0f);
            portraitState.addAnimation(2, "PlasmaRation", true,0.0f);
            portraitState.addAnimation(3, "maskHalo_fade_in", true,0.0f);
            portraitState.addAnimation(3, "maskHalo_loop", true,0.0f);

            if (TheGuardianChan.hexaghostMask){
                hexaghostMask_switch = false;
                portraitState.addAnimation(3, "Mask_off", false,1.0f);
            }

            InitializeStaticPortraitVar();
            if(orbs.size() == 0){
                createOrbs();
                hexaghostChangeState(ACTIVATE_STATE);
            }}

        }else {
            InitializeStaticPortraitVar();
        }
    }
    private static void InitializeStaticPortraitVar(){
        guardianSFX_timer = 12.0f;
        guardianWhirl_played = false;

        ghostFireTimer = ghostFireTimer_time;
        giantGhostFireTimer =giantGhostFireTimer_time;
        orbActiveCount = 0;
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

                    if (portraitAnimationLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                    if (portraitAnimationRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);

                    if (reskinRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                    if (reskinLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);

                    FontHelper.cardTitleFont.getData().setScale(1.0F);
                    FontHelper.bannerFont.getData().setScale(0.8F);
                    FontHelper.renderFontCentered(sb, FontHelper.bannerFont, TEXT[0], Settings.WIDTH / 2.0F - reskinX_center, 850.0F * Settings.scale , Settings.GOLD_COLOR);
                    FontHelper.renderFontCentered(sb, FontHelper.bannerFont, CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("PortraitAnimation")).TEXT[0], Settings.WIDTH / 2.0F - reskinX_center, 970 * Settings.scale , Settings.GOLD_COLOR);

                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[(reskinCount % 2) * ( i + 1 ) + 1], Settings.WIDTH / 2.0F - reskinX_center, 800.0F * Settings.scale , Settings.GOLD_COLOR);
                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, CardCrawlGame.languagePack.getUIString(TheGuardianChan.makeID("PortraitAnimation")).TEXT[disablePortraitAnimationVar], Settings.WIDTH / 2.0F - reskinX_center, 920.0F * Settings.scale , Settings.GOLD_COLOR);

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

                    if (o.name.equals(characterOptionNames.get(i)) && o.selected && reskinCount == 1 && !disablePortraitAnimation) {

                        portraitState.update(Gdx.graphics.getDeltaTime());
                        portraitState.apply(portraitSkeleton);
                        portraitSkeleton.updateWorldTransform();

                        if(i != 2)
                            portraitSkeleton.setPosition(1092.0f * Settings.scale, Settings.HEIGHT - 1032.0f * Settings.scale);   //                       立绘位置

                        if(i == 2)
                            portraitSkeleton.setPosition(1266.0f * Settings.scale, Settings.HEIGHT - 597.0f * Settings.scale);

                        portraitSkeleton.setColor(Color.WHITE);
                        portraitSkeleton.setFlip(false,false);

                    sb.end();
                    CardCrawlGame.psb.begin();
                    sr.draw(CardCrawlGame.psb, portraitSkeleton);
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

                    } }}}}

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

            if(__instance.name.equals(characterOptionNames.get(i)) && !disablePortraitAnimation){
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

                if (o.name.equals(characterOptionNames.get(i)) && o.selected) {
                    __instance.bgCharImg = updateBgImg(i);

                    if (InputHelper.justClickedLeft && reskinLeft.hovered) {
                        reskinLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                    }

                    if (InputHelper.justClickedLeft && reskinRight.hovered) {
                        reskinRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1"); }

                    if (InputHelper.justClickedLeft && portraitAnimationLeft.hovered) {
                        portraitAnimationLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1"); }

                    if (InputHelper.justClickedLeft && portraitAnimationRight.hovered) {
                        portraitAnimationRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1"); }

                    if (reskinLeft.justHovered || reskinRight.justHovered || portraitAnimationLeft.justHovered || portraitAnimationRight.justHovered)
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
                            if(disablePortraitAnimation){
                                disablePortraitAnimationVar = 1;
                            }else {
                                disablePortraitAnimationVar = 2;
                            }
                            disablePortraitAnimation = !disablePortraitAnimation;
                            loadPortraitAnimation(i);
                            __instance.bgCharImg = updateBgImg(i);
                        }

                        if(portraitAnimationRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            portraitAnimationRight.clicked = false;
                            orbs.clear();
                            char_effectsQueue.clear();
                            if(disablePortraitAnimation){
                                disablePortraitAnimationVar = 1;
                            }else {
                                disablePortraitAnimationVar = 2;
                            }
                            disablePortraitAnimation = !disablePortraitAnimation;
                            loadPortraitAnimation(i);
                            __instance.bgCharImg = updateBgImg(i);
                        }

                    if(hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){

                        __instance.bgCharImg = updateBgImg(i);
                    }



                    InitializeReskinCount(i);
//============================
// 蹲酱
                    if(i == 0 && reskinCount == 1 && !disablePortraitAnimation){
                        guardianSFX_timer -= Gdx.graphics.getDeltaTime();
                        if( guardianSFX_timer < 10.05f && !guardianWhirl_played){
                            char_effectsQueue.add(new PortraitWhirlwindEffect(new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.5F, 0.55F),1.0F),false));
                            guardianWhirl_played = true;
                        }

                        if( guardianSFX_timer < 0.0f){
                            CardCrawlGame.sound.playA("MONSTER_GUARDIAN_DESTROY", MathUtils.random(-0.1F, 0.1F));
                            guardianSFX_timer = 12.0f;
                            guardianWhirl_played = false;
                        }
                    }




//-----------------------
//六火

//面具互动

                    if(i == 2 && reskinCount == 1) {
                        if(!disablePortraitAnimation){
                        if (hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            hexaghostMask.clicked = false;

                            if (hexaghostMask_switch) {
                                portraitState.setAnimation(3, "Mask_off", false);
                                hexaghostMask_switch = false;
                                TheGuardianChan.hexaghostMask = true;
                                TheGuardianChan.saveSettings();
                            } else {
                                portraitState.setAnimation(3, "Mask_on", false);
                                portraitState.addAnimation(3, "maskHalo_loop", true, 0.0f);
                                hexaghostMask_switch = true;
                                TheGuardianChan.hexaghostMask = false;
                                TheGuardianChan.saveSettings();
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
                                    TheGuardianChan.hexaghostMask = true;
                                    TheGuardianChan.saveSettings();
                                } else {
                                    hexaghostMask_switch = true;
                                    TheGuardianChan.hexaghostMask = false;
                                    TheGuardianChan.saveSettings();
                                }
                            }
                        }
                    }



// hitbox update


                    reskinLeft.update();
                    reskinRight.update();
                    portraitAnimationLeft.update();
                    portraitAnimationRight.update();
                    hexaghostMask.update();

                }}
            }}}





    private static void InitializeReskinCount(int characterCount){
        switch (characterCount){
            case 0 :
                reflashReskinCount(TheGuardianChan.GuardianOriginalAnimation);
                break;
            case 1:
                reflashReskinCount(TheGuardianChan.SlimeOriginalAnimation);
                break;
            case 2:
                reflashReskinCount(TheGuardianChan.HexaghostOriginalAnimation);
                break;
            case 3:
                reflashReskinCount(TheGuardianChan.SneckoOriginalAnimation);
                break;
            default:
                break;
        }

        if (!(reskinCount == 0 || reskinCount == 1 ))
            reskinCount = 0;
    }



    public static void reflashReskinCount(boolean originalAnimation){
        if(originalAnimation){
            if(reskinCount != 0)
                reskinCount = 0;
        }else {
            if(reskinCount != 1)
                reskinCount = 1;
        }
    }

    public static Texture updateBgImg(int selectedCharCount){

        switch(selectedCharCount){
           case 0:
            switch (reskinCount){
                case 0:
                    TheGuardianChan.GuardianOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return GuardianOriginal;
                case 1:
                    TheGuardianChan.GuardianOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    if(disablePortraitAnimation){
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
                    TheGuardianChan.SlimeOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return SlimeOriginal;
                case 1:
                    TheGuardianChan.SlimeOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    if(disablePortraitAnimation){
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
                    TheGuardianChan.HexaghostOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return hexaghostOriginal;
                case 1:
                    TheGuardianChan.HexaghostOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    if(disablePortraitAnimation){
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
                    TheGuardianChan.SneckoOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return sneckoOriginal;
                case 1:
                    TheGuardianChan.SneckoOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    if(disablePortraitAnimation){
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