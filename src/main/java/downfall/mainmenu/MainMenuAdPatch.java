package downfall.mainmenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import downfall.vfx.PrettyAdEffect;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainMenuAdPatch {

    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString("downfall:MainMenuAd");
    private static final MainMenuAd advert = new MainMenuAd();
    public static final TalesAndTacticsPopup popup = new TalesAndTacticsPopup();

    private static class MainMenuAdInfo {
        private String text;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
        private String textheader;
        private String url;
        private Texture img;

        private MainMenuAdInfo(String text, String text2, String text3, String text4, String text5, String textheader, String url, Texture img) {
            this.text = text;
            this.text2 = text2;
            this.text3 = text3;
            this.text4 = text4;
            this.text5 = text5;
            this.textheader = textheader;
            this.url = url;
            this.img = img;
        }
    }

    private static ArrayList<MainMenuAdInfo> ads = new ArrayList<>();

    static {
        // Star of Providence Info
        ads.add(new MainMenuAdInfo("", "", "", "", "", "", "https://store.steampowered.com/app/603960/Star_of_Providence/", TextureLoader.getTexture("downfallResources/images/menustuff/"+ TalesAndTacticsPopup.langFolder() +"/menuSOP.png")));

        // T9 Game Info
        ads.add(new MainMenuAdInfo("", "", "", "", "", "", "https://store.steampowered.com/app/1652250/Tales__Tactics/", TextureLoader.getTexture("downfallResources/images/menustuff/" + TalesAndTacticsPopup.langFolder() + "/menuTNT.png")));
        // STS Modding Info
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            ads.add(new MainMenuAdInfo(STRINGS.TEXT[0], STRINGS.TEXT[1], STRINGS.TEXT[2], STRINGS.TEXT[3], STRINGS.TEXT[4], STRINGS.TEXT[5], "https://www.bilibili.com/video/BV1QA411j7Kx", null));
        } else if (Settings.language == Settings.GameLanguage.KOR) {
            ads.add(new MainMenuAdInfo(STRINGS.TEXT[0], STRINGS.TEXT[1], STRINGS.TEXT[2], STRINGS.TEXT[3], STRINGS.TEXT[4], STRINGS.TEXT[5], "https://blog.naver.com/2020xodn/222147787489", null));
        } else {// english
            ads.add(new MainMenuAdInfo(STRINGS.TEXT[0], STRINGS.TEXT[1], STRINGS.TEXT[2], STRINGS.TEXT[3], STRINGS.TEXT[4], STRINGS.TEXT[5], "https://DownfallTutorial.github.io", null));
        }
        advert.current = ads.get(0);
    }

    @SpirePatch(clz = TitleBackground.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void renderAd(TitleBackground instance, SpriteBatch sb) {
            if (downfallMod.STEAM_MODE) {
                advert.render(sb);
                if (!popup.done) {
                    popup.render(sb);
                }
            } else {
                popup.done = true;
            }
        }
    }

    @SpirePatch(clz = TitleBackground.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void updateAd(TitleBackground instance) {
            if (downfallMod.STEAM_MODE) {
                advert.update();
                if (!popup.done) {
                    popup.update();
                }
            } else {
                popup.done = true;
            }
        }
    }

    private static class MainMenuAd {
        private static final Texture tex = TextureLoader.getTexture("downfallResources/images/menuPanelHalfBlue.png");
        private MainMenuAdInfo current;

        public final Hitbox hb;
        public final Hitbox togglehb;

        private Color tint = new Color(1, 1, 1, 0);
        private Color btnTint = new Color(1, 1, 1, 0);
        private float xPos = Settings.WIDTH * 0.7F;
        private float yPos = Settings.HEIGHT / 2F;
        private float angle = 0.0f;
        private float scale = 0.5f;
        private float adjustedWidth = tex.getWidth() * Settings.scale * scale;
        private float adjustedHeight = tex.getHeight() * Settings.scale * scale;
        private float buttonX = xPos + adjustedWidth + (25F * Settings.scale);
        private float buttonY = yPos + (adjustedHeight / 2F);
        private float buttonWidth = ImageMaster.CF_RIGHT_ARROW.getWidth();
        private float buttonHeight = ImageMaster.CF_RIGHT_ARROW.getHeight();

        public MainMenuAd() {
            hb = new Hitbox(xPos, yPos, adjustedWidth, adjustedHeight);
            togglehb = new Hitbox(buttonX, buttonY, buttonWidth, buttonHeight);
        }

        public void render(SpriteBatch sb) {

            sb.setColor(Color.WHITE.cpy());

            if (current.img == null) {
                sb.draw(tex, xPos, yPos, 0, 0, tex.getWidth(), tex.getHeight(), Settings.scale * scale, Settings.scale * scale, angle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
                if (tint.a > 0.0f) {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(tint);
                    sb.draw(tex, xPos, yPos, 0, 0, tex.getWidth(), tex.getHeight(), Settings.scale * scale, Settings.scale * scale, angle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
                    sb.setBlendFunction(770, 771);
                }

                FontHelper.cardTitleFont.getData().setScale(1.0f);

                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, current.text, xPos + adjustedWidth / 2, yPos + 360F * Settings.scale);

                FontHelper.cardDescFont_N.getData().setScale(1.0f);
                FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, current.text2, xPos + adjustedWidth / 2, yPos + 290F * Settings.scale);
                FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, current.text3, xPos + adjustedWidth / 2, yPos + 250F * Settings.scale);
                FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, current.text4, xPos + adjustedWidth / 2, yPos + 210F * Settings.scale);
                FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, current.text5, xPos + adjustedWidth / 2, yPos + 170F * Settings.scale);
                FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, current.textheader, xPos + adjustedWidth / 2, yPos + 130F * Settings.scale);

            } else {
                sb.draw(current.img, xPos, yPos, 0, 0, current.img.getWidth(), current.img.getHeight(), Settings.scale, Settings.scale, angle, 0, 0, current.img.getWidth(), current.img.getHeight(), false, false);
                if (tint.a > 0.0f) {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(tint);
                    sb.draw(current.img, xPos, yPos, 0, 0, current.img.getWidth(), current.img.getHeight(), Settings.scale, Settings.scale, angle, 0, 0, current.img.getWidth(), current.img.getHeight(), false, false);
                    sb.setBlendFunction(770, 771);
                }
            }

            sb.setColor(Color.WHITE.cpy());

            sb.draw(ImageMaster.CF_RIGHT_ARROW, buttonX, buttonY, 0, 0, ImageMaster.CF_RIGHT_ARROW.getWidth(), ImageMaster.CF_RIGHT_ARROW.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, ImageMaster.CF_RIGHT_ARROW.getWidth(), ImageMaster.CF_RIGHT_ARROW.getHeight(), false, false);
            if (btnTint.a > 0.0f) {
                sb.setBlendFunction(770, 1);
                sb.setColor(btnTint);
                sb.draw(ImageMaster.CF_RIGHT_ARROW, buttonX, buttonY, 0, 0, ImageMaster.CF_RIGHT_ARROW.getWidth(), ImageMaster.CF_RIGHT_ARROW.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, ImageMaster.CF_RIGHT_ARROW.getWidth(), ImageMaster.CF_RIGHT_ARROW.getHeight(), false, false);
                sb.setBlendFunction(770, 771);
            }

            sb.setColor(Color.WHITE.cpy());

            sparkles.forEach(q -> {
                q.render(sb);
            });

            togglehb.render(sb);
            hb.render(sb);
        }

        private float particleTimer = 0.4F;
        private ArrayList<AbstractGameEffect> sparkles = new ArrayList<>();

        public void update() {
            if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU) {
                return;
            }

            if (!popup.done) {
                return;
            }

            if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.4F;
                    sparkles.add(new PrettyAdEffect(MathUtils.random(xPos, xPos + adjustedWidth), MathUtils.random(yPos, yPos + adjustedHeight)));
                }
            }

            sparkles.forEach(q -> q.update());

            hb.update();
            if (hb.hovered) {
                this.tint.a = 0.25F;
                if (InputHelper.justClickedLeft) {
                    CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                    try {
                        Desktop.getDesktop().browse(new URI(current.url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                this.tint.a = 0.0f;
            }

            togglehb.update();
            if (togglehb.hovered) {
                btnTint.a = 0.25F;
                if (InputHelper.justClickedLeft) {
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    if (advert.current == ads.get(2)) {
                        advert.current = ads.get(0);
                    } else {
                        advert.current = ads.get(ads.indexOf(advert.current) + 1);
                    }
                }
            } else {
                btnTint.a = 0F;
            }
        }
    }
}
