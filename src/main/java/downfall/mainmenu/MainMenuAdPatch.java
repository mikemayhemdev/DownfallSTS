package downfall.mainmenu;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
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
            advert.render(sb);
        }
    }

    @SpirePatch(clz = TitleBackground.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void updateAd(TitleBackground instance) {
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
        private float width = tex.getWidth();
        private float height = tex.getHeight();
        private float xPos = Settings.WIDTH * 0.9F;
        private float xCenteredPos = xPos - (width / 2f);
        private float yPos = Settings.HEIGHT * 0.8F - (height / 2f);
        private float yTextPos = yPos;
        private float angle = 0.0f;

        public MainMenuAd(String text, String textheader, String text2,String text3,String text4,String text5,String url) {
            hb = new Hitbox(xCenteredPos, yPos, width, height);
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
            TextureAtlas.AtlasRegion menuTexture = new TextureAtlas.AtlasRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
            sb.draw(menuTexture, xCenteredPos, yPos, width / 2f, height / 2f, width, height, 1, 1, angle);
            if (tint.a > 0.0f) {
                sb.setBlendFunction(770, 1);
                sb.setColor(tint);
                sb.draw(menuTexture, xCenteredPos, yPos, width / 2f, height / 2f, width, height, 1, 1, angle);
                sb.setBlendFunction(770, 771);
            }
            FontHelper.cardTitleFont.getData().setScale(1.0f);

            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, textheader, xPos, yTextPos + 360F * Settings.scale);

            FontHelper.cardDescFont_N.getData().setScale(1.0f);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text, xPos, yTextPos + 290F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text2, xPos, yTextPos + 250F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text3, xPos, yTextPos + 210F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text4, xPos, yTextPos + 170F * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, text5, xPos, yTextPos + 130F * Settings.scale);

            hb.render(sb);
        }

        public void update() {
            if(CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU) {
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
