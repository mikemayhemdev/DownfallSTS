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

public class TalesAndTacticsPopup {

    private static final float POS_X = Settings.WIDTH * 0.1F;
    private static final float POS_Y = Settings.HEIGHT * 0.1F;
    private static final float BTN_POS_X = Settings.WIDTH * 0.3F;
    private static final float BTN_POS_Y = Settings.HEIGHT * 0.2F;

    public static final Texture bgTex = TextureLoader.getTexture("downfallResources/images/menuPopupBg.png");
    public static final Texture imgTex = TextureLoader.getTexture("downfallResources/images/menuTNTBig.png");
    public static final Texture dismissBtnTex = TextureLoader.getTexture("downfallResources/images/menuTNTDismiss.png");

    private float posY;
    private Color popupBgColor = new Color(1, 1, 1, 0);
    private Hitbox btnHitbox;
    private Hitbox steamHitbox;
    private boolean fadingIn = false;
    private boolean fadingOut = false;
    private float fadeTimer = 0;
    private static final float FADE_TIME = 45;
    public boolean done = true;

    public TalesAndTacticsPopup() {
        posY = Settings.HEIGHT;
        btnHitbox = new Hitbox(BTN_POS_X, BTN_POS_Y, dismissBtnTex.getWidth() * Settings.scale, dismissBtnTex.getHeight() * Settings.scale);
        steamHitbox = new Hitbox(POS_X, POS_Y, 1600 * Settings.scale, 900 * Settings.scale);
        //        if (downfallMod.unseenTutorials[5]) {
        if (true) {
            downfallMod.unseenTutorials[5] = false;
            done = false;
            fadingIn = true;
        }
    }

    public void update() {
        if (fadingIn) {
            posY = MathUtils.lerp(Settings.HEIGHT, POS_Y, fadeTimer / FADE_TIME);
            popupBgColor.a += 0.011;
            fadeTimer += 1;
            if (fadeTimer >= FADE_TIME) {
                fadingIn = false;
                fadeTimer = 0;
            }
        } else if (fadingOut) {
            posY = MathUtils.lerp(POS_Y, Settings.HEIGHT, fadeTimer / FADE_TIME);
            fadeTimer += 1;
            popupBgColor.a -= 0.011;
            if (fadeTimer >= FADE_TIME) {
                fadingOut = false;
                done = true;
            }
        } else {
            steamHitbox.update();
            btnHitbox.update();
            if (btnHitbox.hovered && InputHelper.justClickedLeft) {
                fadingOut = true;
            } else if (steamHitbox.hovered && InputHelper.justClickedLeft) {
                fadingOut = true;
                CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                DiscordButton.openWebpage("https://store.steampowered.com/app/1652250/Tales__Tactics/");
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(popupBgColor);
        drawTextureScaled(sb, bgTex, 0, 0);
        sb.setColor(Color.WHITE);
        drawTextureScaled(sb, imgTex, POS_X, posY);
        if (btnHitbox.hovered) {
            sb.setColor(Color.WHITE.cpy());
        } else {
            sb.setColor(Color.GRAY.cpy());
        }
        drawTextureScaled(sb, dismissBtnTex, btnHitbox.x, btnHitbox.y - (100 * Settings.scale));
        sb.setColor(Color.WHITE);
        steamHitbox.render(sb);
        btnHitbox.render(sb);
    }

    private static void drawTextureScaled(SpriteBatch sb, Texture tex, float x, float y) {
        sb.draw(tex, x, y, 0, 0, tex.getWidth() * Settings.scale, tex.getHeight() * Settings.scale, 1, 1, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }
}
