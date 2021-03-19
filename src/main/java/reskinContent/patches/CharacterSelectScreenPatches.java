package reskinContent.patches;


import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import guardian.patches.GuardianEnum;
import reskinContent.reskinContent;

import reskinContent.helpers.PortraitHexaghostOrb;
import reskinContent.skinCharacter.*;
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
public class CharacterSelectScreenPatches {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(reskinContent.makeID("ReSkin"));
    public static final String[] TEXT = uiStrings.TEXT;


    public static Hitbox reskinRight;
    public static Hitbox reskinLeft;
    public static Hitbox portraitAnimationLeft;
    public static Hitbox portraitAnimationRight;

    private static float reskin_Text_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float reskin_W = reskin_Text_W + 200.0f * Settings.scale;
    private static float reskinX_center = 600.0F * Settings.scale;

    private static boolean bgIMGUpdate = false;

    public static Hitbox hexaghostMask;

    public static ArrayList<AbstractGameEffect> char_effectsQueue = new ArrayList();

    public static ArrayList<AbstractGameEffect> char_effectsQueue_toRemove = new ArrayList();

    public static AbstractSkinCharacter[] characters = new AbstractSkinCharacter[]{
            new GuardianChan(), new Slaifu(), new Hexago(), new SSSSnecko(), new ChampSister(), new Automaton()
    };


    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {
            // Called when you first open the screen, create hitbox for each button
            reskinContent.loadSettings();
            char_effectsQueue.clear();

            reskinRight = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            reskinLeft = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            portraitAnimationLeft = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            portraitAnimationRight = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);


            reskinRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center, 800.0F * Settings.scale);
            reskinLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center, 800.0F * Settings.scale);
            portraitAnimationLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center, 920.0F * Settings.scale);
            portraitAnimationRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center, 920.0F * Settings.scale);

            hexaghostMask = new Hitbox(250.0f * Settings.scale, 350.0f * Settings.scale);
            hexaghostMask.move(1350.0F * Settings.scale, 780.0F * Settings.scale);

        }


        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_Render {
            @SpirePostfixPatch
            public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb) {
                for (CharacterOption o : __instance.options) {
                    for (int i = 0; i <= characters.length - 1; i++) {
                        characters[i].InitializeReskinCount();


                        if (o.name.equals(characters[i].name) && o.selected) {
                            reskinRight.render(sb);
                            reskinLeft.render(sb);
                            portraitAnimationLeft.render(sb);
                            portraitAnimationRight.render(sb);

                            if (i == 2 && characters[i].reskinCount == 1)
                                hexaghostMask.render(sb);

                            if (characters[i].reskinCount != 0 && characters[i].reskinUnlock) {
                                if (portraitAnimationLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                                if (portraitAnimationRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);

                            }

                            if (characters[i].reskinUnlock) {
                                if (reskinRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                                if (reskinLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                            }

                            FontHelper.cardTitleFont.getData().setScale(1.0F);
                            FontHelper.losePowerFont.getData().setScale(0.8F);

                            if (characters[i].reskinCount != 0 && characters[i].reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimationType")).TEXT[characters[i].portraitAnimationType], Settings.WIDTH / 2.0F - reskinX_center, 920.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimation")).TEXT[0], Settings.WIDTH / 2.0F - reskinX_center, 970 * Settings.scale, Settings.GOLD_COLOR);
                            }
                            if (characters[i].reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, TEXT[0], Settings.WIDTH / 2.0F - reskinX_center, 850.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[(characters[i].reskinCount % 2) * (i + 1) + 1], Settings.WIDTH / 2.0F - reskinX_center, 800.0F * Settings.scale, Settings.GOLD_COLOR.cpy());

                            }


                        }
                    }
                }
            }

        }

        //                动态立绘
        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_portraitSkeleton {
            @SpireInsertPatch(rloc = 62)
            public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb) {

                for (CharacterOption o : __instance.options) {
                    for (AbstractSkinCharacter c : characters) {
                        c.InitializeReskinCount();
                        if (o.name.equals(c.name) && o.selected && c.reskinCount == 1 && c.portraitAnimationType != 0) {
                            c.render(sb);

                            if (char_effectsQueue.size() > 0) {
                                for (int k = 0; k < char_effectsQueue.size(); k++) {
                                    if (!char_effectsQueue.get(k).isDone) {
                                        char_effectsQueue.get(k).render(sb);
                                        char_effectsQueue.get(k).update();
                                    } else {
                                        if (char_effectsQueue_toRemove == null)
                                            char_effectsQueue_toRemove = new ArrayList<>();
                                        if (!char_effectsQueue_toRemove.contains(char_effectsQueue.get(k)))
                                            char_effectsQueue_toRemove.add(char_effectsQueue.get(k));
                                    }
                                }
                                if (char_effectsQueue_toRemove != null)
                                    char_effectsQueue.removeAll(char_effectsQueue_toRemove);
                            }
                        }
                    }

                }

            }
        }
    }

//    立绘动画重置

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")

    public static class CharacterOptionPatch_reloadAnimation {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance) {
            char_effectsQueue.clear();
            bgIMGUpdate = false;

            for (AbstractSkinCharacter c : characters) {
                c.InitializeReskinCount();
                if (__instance.name.equals(c.name) && c.portraitAnimationType != 0) {
                    c.clearOrbs();
                    if (c.reskinCount != 0)
                        c.loadPortraitAnimation();
                    System.out.println("立绘载入2");
                }
            }
        }
    }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {

            for (CharacterOption o : __instance.options) {
                for (AbstractSkinCharacter c : characters) {
                    c.InitializeReskinCount();

                    if (o.name.equals(c.name) && o.selected && c.reskinUnlock) {
                        if (!bgIMGUpdate) {
                            __instance.bgCharImg = c.updateBgImg();
                            bgIMGUpdate = true;
                        }

                        if (InputHelper.justClickedLeft && reskinLeft.hovered) {
                            reskinLeft.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (InputHelper.justClickedLeft && reskinRight.hovered) {
                            reskinRight.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (InputHelper.justClickedLeft && portraitAnimationLeft.hovered && c.reskinCount != 0) {
                            portraitAnimationLeft.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (InputHelper.justClickedLeft && portraitAnimationRight.hovered && c.reskinCount != 0) {
                            portraitAnimationRight.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (reskinLeft.justHovered || reskinRight.justHovered)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

                        if ((portraitAnimationLeft.justHovered || portraitAnimationRight.justHovered) && c.reskinCount != 0)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

                        if (InputHelper.justClickedLeft && hexaghostMask.hovered && c.reskinCount != 0)
                            hexaghostMask.clickStarted = true;


                        if (reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            reskinRight.clicked = false;
                            c.clearOrbs();
                            char_effectsQueue.clear();

                            if (c.reskinCount < 1) {
                                c.reskinCount += 1;
                            } else {
                                c.reskinCount = 0;
                            }
                            c.loadPortraitAnimation();
                            __instance.bgCharImg = c.updateBgImg();
                        }


                        if (reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            reskinLeft.clicked = false;
                            c.clearOrbs();
                            char_effectsQueue.clear();

                            if (c.reskinCount > 0) {
                                c.reskinCount -= 1;
                            } else {
                                c.reskinCount = 1;
                            }
                            c.loadPortraitAnimation();
                            __instance.bgCharImg = c.updateBgImg();
                        }


                        if (portraitAnimationLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            portraitAnimationLeft.clicked = false;
                            c.clearOrbs();
                            char_effectsQueue.clear();
                            if (c.portraitAnimationType <= 0) {
                                c.portraitAnimationType = 2;
                            } else {
                                c.portraitAnimationType -= 1;
                            }
                            c.loadPortraitAnimation();
                            __instance.bgCharImg = c.updateBgImg();
                        }

                        if (portraitAnimationRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            portraitAnimationRight.clicked = false;
                            c.clearOrbs();
                            char_effectsQueue.clear();
                            if (c.portraitAnimationType >= 2) {
                                c.portraitAnimationType = 0;
                            } else {
                                c.portraitAnimationType += 1;
                            }
                            c.loadPortraitAnimation();
                            __instance.bgCharImg = c.updateBgImg();
                        }

                        if (hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {

                            __instance.bgCharImg = c.updateBgImg();
                        }


                        c.InitializeReskinCount();


                        c.update();

                        reskinLeft.update();
                        reskinRight.update();

                        if (c.reskinCount != 0) {
                            portraitAnimationLeft.update();
                            portraitAnimationRight.update();
                        }

                        hexaghostMask.update();
                    }
                }
            }

        }
    }
}