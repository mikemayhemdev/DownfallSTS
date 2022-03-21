package downfall.mainmenu;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import downfall.downfallMod;
import downfall.util.TextureLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenuAdPatch {

    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString("downfall:MainMenuAd");
    private static final MainMenuAd advert = new MainMenuAd(STRINGS.TEXT[1], STRINGS.TEXT[0], STRINGS.TEXT[2], STRINGS.TEXT[3], STRINGS.TEXT[4], STRINGS.TEXT[5], "https://DownfallTutorial.github.io");

    @SpirePatch(clz = TitleBackground.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void renderAd(TitleBackground instance, SpriteBatch sb) {
            if (downfallMod.STEAM_MODE)
                advert.render(sb);
        }
    }

    @SpirePatch(clz = TitleBackground.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void updateAd(TitleBackground instance) {
            if (downfallMod.STEAM_MODE)
                advert.update();
        }
    }

    private static class MainMenuAd {
        private String text;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
        private String textheader;
        private String url;
        private static final Texture tex = TextureLoader.getTexture("downfallResources/images/menuPanelHalfBlue.png");

        public final Hitbox hb;

        private Color tint = new Color(1, 1, 1, 0);
        private float xPos = Settings.WIDTH * 0.6F;
        private float yPos = Settings.HEIGHT / 2F;
        private float angle = 0.0f;
        private float scale = 0.5f;
        private float adjustedWidth = tex.getWidth() * Settings.scale * scale;
        private float adjustedHeight = tex.getHeight() * Settings.scale * scale;

        public MainMenuAd(String text, String textheader, String text2, String text3, String text4, String text5, String url) {
            hb = new Hitbox(xPos, yPos, adjustedWidth, adjustedHeight);
            this.text = text;
            this.url = url;
            this.textheader = textheader;
            this.text2 = text2;
            this.text3 = text3;
            this.text4 = text4;
            this.text5 = text5;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE.cpy());
            sb.draw(tex, xPos, yPos, 0, 0, tex.getWidth(), tex.getHeight(), Settings.scale * scale, Settings.scale * scale, angle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
            if (tint.a > 0.0f) {
                sb.setBlendFunction(770, 1);
                sb.setColor(tint);
                sb.draw(tex, xPos, yPos, 0, 0, tex.getWidth(), tex.getHeight(), Settings.scale * scale, Settings.scale * scale, angle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
                sb.setBlendFunction(770, 771);
            }
            FontHelper.cardTitleFont.getData().setScale(1.0f);

            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, textheader, xPos+ adjustedWidth / 2, yPos + 360F * Settings.scale);

            FontHelper.cardDescFont_N.getData().setScale(1.0f);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text, xPos + adjustedWidth / 2, yPos + 290F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text2, xPos + adjustedWidth / 2, yPos + 250F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text3, xPos + adjustedWidth / 2, yPos + 210F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text4, xPos + adjustedWidth / 2, yPos + 170F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text5, xPos + adjustedWidth / 2, yPos + 130F * Settings.scale);

            hb.render(sb);
        }

        public void update() {
            if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU) {
                return;
            }
            hb.update();
            if (hb.hovered) {
                this.tint.a = 0.25F;
                if (InputHelper.justClickedLeft) {
                    CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                this.tint.a = 0.0f;
            }
        }
    }
}
