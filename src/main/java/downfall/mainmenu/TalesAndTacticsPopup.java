package downfall.mainmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import downfall.downfallMod;
import downfall.util.TextureLoader;

import java.io.IOException;

public class TalesAndTacticsPopup {

    private static final float POS_X = Settings.WIDTH * 0.2F;
    private static final float POS_Y = Settings.HEIGHT * 0.2F;

    public static String langFolder() {
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            return "zhs";
        }
        return "eng";
    }

    public static final Texture bgTex = TextureLoader.getTexture("downfallResources/images/menustuff/" + langFolder() + "/menuPopupBg.png");
    public static final Texture imgTex = TextureLoader.getTexture("downfallResources/images/menustuff/" + langFolder() + "/menuTNTBig.png");
    public static final Texture dismissBtnTex = TextureLoader.getTexture("downfallResources/images/menustuff/" + langFolder() + "/menuTNTDismiss.png");
    public static final Texture discordBtnTex = TextureLoader.getTexture("downfallResources/images/menustuff/" + langFolder() + "/menuDiscord.png");
    public static final Texture steamBtnTex = TextureLoader.getTexture("downfallResources/images/menustuff/" + langFolder() + "/menuSteam.png");

    private float posY;
    private Color popupBgColor = new Color(1, 1, 1, 0);
    private Hitbox btnHitbox;
    private Hitbox steamHitbox;
    private Hitbox discordHitbox;
    private boolean fadingIn = false;
    private boolean fadingOut = false;
    private float fadeTimer = 0;
    private static final float FADE_TIME = 30;
    public boolean done = true;

    public TalesAndTacticsPopup() {
        posY = Settings.HEIGHT;
        btnHitbox = new Hitbox(POS_X + (986 * Settings.scale), POS_Y + (600 * Settings.scale), dismissBtnTex.getWidth() * Settings.scale, dismissBtnTex.getHeight() * Settings.scale);
        steamHitbox = new Hitbox(POS_X + (662 * Settings.scale), POS_Y + (49 * Settings.scale), steamBtnTex.getWidth() * Settings.scale, steamBtnTex.getHeight() * Settings.scale);
        discordHitbox = new Hitbox(POS_X + (147 * Settings.scale), POS_Y + (46 * Settings.scale), discordBtnTex.getWidth() * Settings.scale, discordBtnTex.getHeight() * Settings.scale);
        if (!downfallMod.STEAM_MODE){

            if (downfallMod.unseenTutorials[5]) {
                downfallMod.unseenTutorials[5] = false;
                done = false;
                fadingIn = true;
                try {
                    downfallMod.saveTutorialsSeen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void update() {
        if (fadingIn) {
            posY = MathUtils.lerp(Settings.HEIGHT, POS_Y, fadeTimer / FADE_TIME);
            popupBgColor.a += 0.025;
            fadeTimer += 1;
            if (fadeTimer >= FADE_TIME) {
                fadingIn = false;
                fadeTimer = 0;
            }
        } else if (fadingOut) {
            posY = MathUtils.lerp(POS_Y, Settings.HEIGHT, fadeTimer / FADE_TIME);
            fadeTimer += 1;
            popupBgColor.a -= 0.025;
            if (fadeTimer >= FADE_TIME) {
                fadingOut = false;
                done = true;
            }
        } else {
            steamHitbox.update();
            btnHitbox.update();
            discordHitbox.update();
            if (steamHitbox.justHovered || btnHitbox.justHovered || discordHitbox.justHovered) {
                CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
            }
            if (btnHitbox.hovered && InputHelper.justClickedLeft) {
                fadingOut = true;
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
            } else if (steamHitbox.hovered && InputHelper.justClickedLeft) {
                CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                DiscordButton.openWebpage("https://store.steampowered.com/app/1652250/Tales__Tactics/");
            } else if (discordHitbox.hovered && InputHelper.justClickedLeft) {
                CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                DiscordButton.openWebpage("https://discord.gg/g7Bv9gttpp");
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(popupBgColor);
        drawTextureScaled(sb, bgTex, 0, 0, 1);
        sb.setColor(Color.WHITE);
        drawTextureScaled(sb, imgTex, POS_X, posY, 0.75F);
        if (!fadingIn && !fadingOut) {
            if (btnHitbox.hovered) {
                sb.setColor(Color.WHITE.cpy());
            } else {
                sb.setColor(Color.GRAY.cpy());
            }
            drawTextureScaled(sb, dismissBtnTex, btnHitbox.x, btnHitbox.y, 0.8F);
            if (discordHitbox.hovered) {
                sb.setColor(Color.WHITE.cpy());
            } else {
                sb.setColor(Color.GRAY.cpy());
            }
            drawTextureScaled(sb, discordBtnTex, discordHitbox.x, discordHitbox.y, 0.8F);
            if (steamHitbox.hovered) {
                sb.setColor(Color.WHITE.cpy());
            } else {
                sb.setColor(Color.GRAY.cpy());
            }
            drawTextureScaled(sb, steamBtnTex, steamHitbox.x, steamHitbox.y, 0.8F);
            sb.setColor(Color.WHITE);
            steamHitbox.render(sb);
            btnHitbox.render(sb);
            discordHitbox.render(sb);
        }
    }

    private static void drawTextureScaled(SpriteBatch sb, Texture tex, float x, float y, float scaleMod) {
        sb.draw(tex, x, y, 0, 0, tex.getWidth() * Settings.scale * scaleMod, tex.getHeight() * Settings.scale * scaleMod, 1, 1, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }
}
