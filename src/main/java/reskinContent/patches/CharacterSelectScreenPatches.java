package reskinContent.patches;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import reskinContent.reskinContent;


import reskinContent.skinCharacter.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import java.util.ArrayList;

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
    public static float allTextInfoX = 0.0f;

    private static boolean bgIMGUpdate = false;


    public static ArrayList<AbstractGameEffect> char_effectsQueue = new ArrayList();

    public static ArrayList<AbstractGameEffect> char_effectsQueue_toRemove = new ArrayList();

    public static AbstractSkinCharacter[] characters = new AbstractSkinCharacter[]{
            new GuardianSkin(),
            new SlimeBoundSkin(),
            new HexaghostSkin(),
            new SneckoSkin(),
            new ChampSkin(),
            new AutomatonSkin()
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


            reskinRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX, 800.0F * Settings.scale);
            reskinLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX, 800.0F * Settings.scale);
            portraitAnimationLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX, 920.0F * Settings.scale);
            portraitAnimationRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX, 920.0F * Settings.scale);


        }

        @SpirePatch(
                clz = CharacterOption.class,
                method = "renderInfo"
        )
        public static class CharacterOptionRenderInfoPatch {
            @SpireInsertPatch(locator = renderRelicsLocator.class, localvars = {"infoX", "c", "flavorText"})
            public static SpireReturn<Void> Insert(CharacterOption _instance, SpriteBatch sb, float infoX, AbstractPlayer p, @ByRef String[] flavorText) {
                allTextInfoX = infoX - 200.0f * Settings.scale;
                for (AbstractSkinCharacter character : characters) {
                    if (p.name.equals(character.id)) {
                        flavorText[0] = character.skins[character.reskinCount].DESCRIPTION;
                    }
                }
                return SpireReturn.Continue();
            }
        }

        private static class renderRelicsLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(CharacterOption.class, "renderRelics");
                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
                return lines;
            }
        }


        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_Render {
            @SpirePostfixPatch
            public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb) {
                for (CharacterOption o : __instance.options) {
                    for (AbstractSkinCharacter c : characters) {
                        c.InitializeReskinCount();


                        if (o.name.equals(c.id) && o.selected) {
                            reskinRight.render(sb);
                            reskinLeft.render(sb);
                            portraitAnimationLeft.render(sb);
                            portraitAnimationRight.render(sb);

                            c.skins[c.reskinCount].extraHitboxRender(sb);
//   动画类型箭头渲染
                            if (c.skins[c.reskinCount].hasAnimation() && c.reskinUnlock) {
                                if (portraitAnimationLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale + allTextInfoX, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);

                                if (portraitAnimationRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale + allTextInfoX, 920.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);

                            }
//   动画类型箭头渲染
//   皮肤选择箭头渲染
                            if (c.reskinUnlock) {
                                if (reskinRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale + allTextInfoX, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                                if (reskinLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center - 36.0f * Settings.scale + allTextInfoX, 800.0F * Settings.scale - 36.0f * Settings.scale, 0.0f, 0.0f, 48.0f, 48.0f, Settings.scale * 1.5f, Settings.scale * 1.5f, 0.0F, 0, 0, 48, 48, false, false);
                            }
//   皮肤选择箭头渲染

                            FontHelper.cardTitleFont.getData().setScale(1.0F);
                            FontHelper.losePowerFont.getData().setScale(0.8F);

                            if (c.skins[c.reskinCount].hasAnimation() && c.reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimationType")).TEXT[c.skins[c.reskinCount].portraitAnimationType], Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, 920.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimation")).TEXT[0], Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, 970 * Settings.scale, Settings.GOLD_COLOR);
                            }

                            if (c.reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, TEXT[0], Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, 850.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, c.skins[c.reskinCount].NAME, Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, 800.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
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
                        if (o.name.equals(c.id) && o.selected && c.skins[c.reskinCount].hasAnimation() && c.skins[c.reskinCount].portraitAnimationType != 0) {
                            c.skins[c.reskinCount].render(sb);

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
                if (__instance.name.equals(c.id) && c.skins[c.reskinCount].portraitAnimationType != 0) {
                    c.skins[c.reskinCount].clearWhenClick();
                    if (c.skins[c.reskinCount].hasAnimation())
                        c.skins[c.reskinCount].loadPortraitAnimation();
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

                    if (o.name.equals(c.id) && o.selected && c.reskinUnlock) {
                        if (!bgIMGUpdate) {
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
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

                        if (InputHelper.justClickedLeft && portraitAnimationLeft.hovered && c.reskinCount > 0) {
                            portraitAnimationLeft.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (InputHelper.justClickedLeft && portraitAnimationRight.hovered && c.reskinCount > 0) {
                            portraitAnimationRight.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (reskinLeft.justHovered || reskinRight.justHovered)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

                        if ((portraitAnimationLeft.justHovered || portraitAnimationRight.justHovered) && c.reskinCount > 0)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);


                        reskinRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX, 800.0F * Settings.scale);
                        reskinLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX, 800.0F * Settings.scale);
                        portraitAnimationLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX, 920.0F * Settings.scale);
                        portraitAnimationRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX, 920.0F * Settings.scale);

                        reskinLeft.update();
                        reskinRight.update();

                        if (c.reskinCount > 0) {
                            portraitAnimationLeft.update();
                            portraitAnimationRight.update();
                        }


                        if (reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            reskinRight.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            char_effectsQueue.clear();

                            if (c.reskinCount < c.skins.length - 1) {
                                c.reskinCount += 1;
                            } else {
                                c.reskinCount = 0;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }


                        if (reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            reskinLeft.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            char_effectsQueue.clear();

                            if (c.reskinCount > 0) {
                                c.reskinCount -= 1;
                            } else {
                                c.reskinCount = c.skins.length - 1;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }


                        if (portraitAnimationLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            portraitAnimationLeft.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            char_effectsQueue.clear();
                            if (c.skins[c.reskinCount].portraitAnimationType <= 0) {
                                c.skins[c.reskinCount].portraitAnimationType = 2;
                            } else {
                                c.skins[c.reskinCount].portraitAnimationType -= 1;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }

                        if (portraitAnimationRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            portraitAnimationRight.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            char_effectsQueue.clear();
                            if (c.skins[c.reskinCount].portraitAnimationType >= 2) {
                                c.skins[c.reskinCount].portraitAnimationType = 0;
                            } else {
                                c.skins[c.reskinCount].portraitAnimationType += 1;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }

                        c.skins[c.reskinCount].update();

                        if (c.skins[c.reskinCount].extraHitboxClickCheck()) {
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }


                        c.InitializeReskinCount();


                    }
                }
            }

        }
    }
}