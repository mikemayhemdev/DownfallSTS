package downfall.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import downfall.util.TextureLoader;

public class MainMenuOneTimePopup {

    public static boolean has_shown = false;
    public boolean isShown = false;

    private Hitbox hb;

    private Texture adImg = TextureLoader.getTexture("downfallResources/images/ui/collector-release-ad.png");
    private Texture closeImg = TextureLoader.getTexture("downfallResources/images/ui/collector-release-close.png");

    private static final float POS_X = Settings.WIDTH / 4;
    private static final float POS_Y = Settings.HEIGHT / 4;

    public MainMenuOneTimePopup() {
        hb = new Hitbox(POS_X + adImg.getWidth(), POS_Y, closeImg.getWidth() * Settings.scale, closeImg.getHeight() * Settings.scale);
    }

    public void render(SpriteBatch sb) {
        if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU) {
            return;
        }

        if (isShown) {
            drawTextureScaled(sb, adImg, POS_X, POS_Y);
            drawTextureScaled(sb, closeImg, POS_X + adImg.getWidth() - (30 * Settings.scale), POS_Y);
        }
    }

    public void update() {
        if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU) {
            return;
        }

        hb.update();
        if (hb.hovered && InputHelper.justClickedLeft) {
            close();
        }
    }

    private void close() {
        isShown = false;
    }

    public static void drawTextureScaled(SpriteBatch sb, Texture tex, float x, float y) {
        sb.draw(tex, x, y, 0, 0, tex.getWidth() * Settings.scale, tex.getHeight() * Settings.scale, 1, 1, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }
}
