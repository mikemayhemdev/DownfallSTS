package TheGuardianChan.patches;

import TheGuardianChan.TheGuardianChan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import slimebound.SlimeboundMod;


import java.lang.reflect.Field;
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
    private static float reskin_RIGHT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);
    private static float reskin_LEFT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float X_fixed = 30.0f *Settings.scale;

    public static Field charInfoField;


//    portrait img
//2 is for animated portrait

//The Guardian
    private static Texture GuardianOriginal =  ImageMaster.loadImage(GuardianMod.getResourcePath("charSelect/portrait.png"));
    private static Texture GuardianChan =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/GuardianMod/portrait_waifu.png"));
    private static Texture GuardianChan2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/GuardianMod/portrait_waifu2.png"));

    private static Texture  portraitFix =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/GuardianMod/portrait_waifu_fix.png"));

//Slime Boss
    private static Texture SlimeOriginal =  ImageMaster.loadImage(SlimeboundMod.getResourcePath("charSelect/portrait.png"));
    private static Texture SlaifuTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/Slimebound/portrait_waifu.png"));
    private static Texture SlaifuTexture2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/Slimebound/portrait_waifu2.png"));

//Hexaghost
    private static Texture hexaghostOriginal =  ImageMaster.loadImage("hexamodResources/images/charSelect/charBG.png");
    private static Texture hexaghostTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/HexaghostMod/portrait_waifu.png"));
    private static Texture hexaghostTexture2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/HexaghostMod/portrait_waifu2.png"));

//Snecko
    private static Texture sneckoOriginal =  ImageMaster.loadImage("sneckomodResources/images/charSelect/portrait.png");
    private static Texture sneckoTexture =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/SneckoMod/portrait_waifu.png"));
    private static Texture sneckoTexture2 =  ImageMaster.loadImage(TheGuardianChan.assetPath("img/SneckoMod/portrait_waifu2.png"));


    private static TextureAtlas portraitAtlas = null ;
    private static Skeleton portraitSkeleton;
    private static AnimationState portraitState ;
    private static AnimationStateData portraitStateData;
    private static SkeletonData portraitData;

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
        portraitAtlasMaps.put( 2 ,TheGuardianChan.assetPath("img/GuardianMod/animation/GuardianChan_portrait"));
        portraitAtlasMaps.put( 3 ,TheGuardianChan.assetPath("img/GuardianMod/animation/GuardianChan_portrait"));

    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            // Called when you first open the screen, create hitbox for each button
            reskinRight = new Hitbox(reskin_RIGHT_W - 10.0F * Settings.scale + X_fixed, 50.0F * Settings.scale);
            reskinLeft = new Hitbox(reskin_RIGHT_W - 10.0F * Settings.scale + X_fixed, 50.0F * Settings.scale);
            reskinRight.move(Settings.WIDTH / 2.0F - reskin_RIGHT_W / 2.0F - 550.0F * Settings.scale + 16.0f*Settings.scale + X_fixed, 800.0F * Settings.scale);
            reskinLeft.move(Settings.WIDTH / 2.0F - reskin_LEFT_W / 2.0F - 800.0F * Settings.scale + 16.0f*Settings.scale + X_fixed, 800.0F * Settings.scale);

            if(!disablePortraitAnimation)
            loadPortraitAnimation(0);

        }
    }
//载入动态立绘
    private static void loadPortraitAnimation(Integer characterCount) {
        portraitAtlas = new TextureAtlas(Gdx.files.internal(portraitAtlasMaps.get(characterCount)) + ".atlas");
        SkeletonJson json = new SkeletonJson(portraitAtlas);
        json.setScale(Settings.scale / 1.0F);
        portraitData = json.readSkeletonData(Gdx.files.internal(portraitAtlasMaps.get(characterCount) + ".json"));


        portraitSkeleton = new Skeleton(portraitData);
        portraitSkeleton.setColor(Color.WHITE);
        portraitStateData = new AnimationStateData(portraitData);
        portraitState = new AnimationState(portraitStateData);
        portraitStateData.setDefaultMix(0.2F);

        portraitState.setTimeScale(0.5f);
        portraitState.setAnimation(1, "fade_in", false);
        portraitState.addAnimation(0, "idle", true,0.0f);
    }



    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenPatch_Render
    {
        @SpirePostfixPatch
        public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb)
        {
            // Render your buttons/images by passing SpriteBatch
            if (!(reskinCount == 0 || reskinCount == 1 ))
            {reskinCount = 0;}

            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 3; i++){


                if (o.name.equals(characterOptionNames.get(i)) && o.selected) {

                    reskinRight.render(sb);
                    reskinLeft.render(sb);

                    if (reskinRight.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F - reskin_RIGHT_W / 2.0F - 550.0F * Settings.scale + X_fixed, 800.0F * Settings.scale - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale*2.0f, Settings.scale*2.0f, 0.0F, 0, 0, 48, 48, false, false);
                    if (reskinLeft.hovered || Settings.isControllerMode) {sb.setColor(Color.WHITE);} else {sb.setColor(Color.LIGHT_GRAY);}
                    sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_LEFT_W / 2.0F - 800.0F * Settings.scale + X_fixed, 800.0F * Settings.scale - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale*2.0f, Settings.scale*2.0f, 0.0F, 0, 0, 48, 48, false, false);

                    FontHelper.cardTitleFont.getData().setScale(1.0F);
                    FontHelper.bannerFont.getData().setScale(0.8F);
                    FontHelper.renderFontCentered(sb, FontHelper.bannerFont, TEXT[0], Settings.WIDTH / 2.0F - 660.0F * Settings.scale, 850.0F * Settings.scale , Settings.GOLD_COLOR);

                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[(reskinCount % 2) * ( i + 1 ) + 1], Settings.WIDTH / 2.0F - 660.0F * Settings.scale, 800.0F * Settings.scale , Settings.GOLD_COLOR);

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
            if (!(reskinCount == 0 || reskinCount == 1 ))
            {reskinCount = 0;}

            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 3; i++){


                    if (o.name.equals(characterOptionNames.get(i)) && o.selected && reskinCount == 1 && !disablePortraitAnimation) {

                        portraitState.update(Gdx.graphics.getDeltaTime());
                        portraitState.apply(portraitSkeleton);
                        portraitSkeleton.updateWorldTransform();
                        portraitSkeleton.setPosition(1092.0f * Settings.scale,Settings.HEIGHT- 1032.0f * Settings.scale);
                        portraitSkeleton.setColor(Color.WHITE);
                        portraitSkeleton.setFlip(false,false);

                    sb.end();
                    CardCrawlGame.psb.begin();
                    sr.draw(CardCrawlGame.psb, portraitSkeleton);
                    CardCrawlGame.psb.end();
                    sb.begin();


                        sb.draw(portraitFix, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
                    }



                }
            }
        }

    }

//    立绘动画重置

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")

    public static class CharacterOptionPatch_reloadAnimation
    {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance)
        {
            for(int i = 0; i <= 3; i++){
            if(__instance.name.equals(characterOptionNames.get(i)) && !disablePortraitAnimation){
                loadPortraitAnimation(i);
            }
            }
        }


    }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance)
        {
            // Update your buttons position, check if the player clicked them, and do something if they did
            for (CharacterOption o : __instance.options) {
                for(int i = 0; i <= 3; i++){
                if (o.name.equals(characterOptionNames.get(i)) && o.selected) {


                    if (InputHelper.justClickedLeft && reskinLeft.hovered) {
                        reskinLeft.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                        if(reskinCount == 0 && !disablePortraitAnimation){
                            loadPortraitAnimation(i);
                        }
                    }
                    if (InputHelper.justClickedLeft && reskinRight.hovered) {
                        reskinRight.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                        if(reskinCount == 0 && !disablePortraitAnimation ){
                            loadPortraitAnimation(i);
                        }
                    }

                    if (reskinLeft.justHovered || reskinRight.justHovered) {
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75f);
                    }
//==================================
                    if (!(reskinCount == 0 ||reskinCount == 1 ))
                    {reskinCount = 0;}




                    switch (i){
                        case 0 :
                            if(TheGuardianChan.GuardianOriginalAnimation ){
                                if(reskinCount != 0)   reskinCount = 0;

                                __instance.bgCharImg = updateBgImg(i);

                                if(__instance.bgCharImg != GuardianOriginal ){
                                        __instance.bgCharImg = GuardianOriginal;
                                    }


                            }else {
                                if(reskinCount != 1) reskinCount = 1;

                                __instance.bgCharImg = updateBgImg(i);

                                if(disablePortraitAnimation){
                                        if(__instance.bgCharImg != GuardianChan ){
                                            __instance.bgCharImg = GuardianChan;
                                        }
                                    }else {
                                        if (__instance.bgCharImg != GuardianChan2) {
                                            __instance.bgCharImg = GuardianChan2;
                                        }
                                    }

                            }
                            break;

                        case 1:
                            if(TheGuardianChan.SlimeOriginalAnimation ){
                                if(reskinCount != 0)   reskinCount = 0;

                                __instance.bgCharImg = updateBgImg(i);

                                if(__instance.bgCharImg != SlimeOriginal ){
                                    __instance.bgCharImg = SlimeOriginal;
                                }


                            }else {
                                if(reskinCount != 1) reskinCount = 1;

                                __instance.bgCharImg = updateBgImg(i);

                                if(disablePortraitAnimation){
                                    if(__instance.bgCharImg != SlaifuTexture ){
                                        __instance.bgCharImg = SlaifuTexture;
                                    }
                                }else {
                                    if (__instance.bgCharImg != SlaifuTexture2) {
                                        __instance.bgCharImg = SlaifuTexture2;
                                    }
                                }

                            }
                            break;

                        case 2:
                            if(TheGuardianChan.HexaghostOriginalAnimation ){
                                if(reskinCount != 0)   reskinCount = 0;

                                __instance.bgCharImg = updateBgImg(i);

                                if(__instance.bgCharImg != hexaghostOriginal ){
                                    __instance.bgCharImg = hexaghostOriginal;
                                }


                            }else {
                                if(reskinCount != 1) reskinCount = 1;

                                __instance.bgCharImg = updateBgImg(i);

                                if(disablePortraitAnimation){
                                    if(__instance.bgCharImg != hexaghostTexture ){
                                        __instance.bgCharImg = hexaghostTexture;
                                    }
                                }else {
                                    if (__instance.bgCharImg != hexaghostTexture2) {
                                        __instance.bgCharImg = hexaghostTexture2;
                                    }
                                }

                            }
                            break;

                        case 3:
                            if(TheGuardianChan.SneckoOriginalAnimation ){
                                if(reskinCount != 0)   reskinCount = 0;

                                __instance.bgCharImg = updateBgImg(i);

                                if(__instance.bgCharImg != sneckoOriginal ){
                                    __instance.bgCharImg = sneckoOriginal;
                                }


                            }else {
                                if(reskinCount != 1) reskinCount = 1;

                                __instance.bgCharImg = updateBgImg(i);

                                if(disablePortraitAnimation){
                                    if(__instance.bgCharImg != sneckoTexture ){
                                        __instance.bgCharImg = sneckoTexture;
                                    }
                                }else {
                                    if (__instance.bgCharImg != sneckoTexture2) {
                                        __instance.bgCharImg = sneckoTexture2;
                                    }
                                }
                            }
                            break;

                        default:
                            break;
                    }

//==================================




                        if(reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            reskinRight.clicked = false;
                            if (reskinCount < 1) {
                                reskinCount += 1;
                            } else {
                                reskinCount = 0;
                            }
                            __instance.bgCharImg = updateBgImg(i);
                        }

                        if(reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()){
                            reskinLeft.clicked = false;
                            if (reskinCount > 0) {
                                reskinCount -= 1;
                            } else {
                                reskinCount = 1;
                            }
                            __instance.bgCharImg = updateBgImg(i);
                        }


                    reskinLeft.update();
                    reskinRight.update();
                }
            }
            }
        }
    }




    public static Texture updateBgImg(int selectedCharCount){
        if(selectedCharCount == 0){
            switch (reskinCount ){
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
        }
        else if (selectedCharCount == 1){
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
        }
        else if (selectedCharCount == 2){
            switch (reskinCount ){
                case 0:
                    TheGuardianChan.HexaghostOriginalAnimation = true;
                    TheGuardianChan.saveSettings();
                    return hexaghostOriginal;
                case 1:
                    TheGuardianChan.HexaghostOriginalAnimation = false;
                    TheGuardianChan.saveSettings();
                    if(disablePortraitAnimation){
                        return hexaghostTexture;
                    }else {
                        return hexaghostTexture2;
                    }
                default:
                    return hexaghostOriginal;
            }
        }
        else if (selectedCharCount == 3){
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
        }
        else {
            return null;
        }

        }

}