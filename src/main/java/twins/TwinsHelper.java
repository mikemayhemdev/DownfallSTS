package twins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TwinsHelper {
    public static final float ICON_W = 64.0F * Settings.scale;
    public static final float ICON_Y = Settings.isMobile ? (float)Settings.HEIGHT - ICON_W - 12.0F * Settings.scale : (float)Settings.HEIGHT - ICON_W;
    public static final float TOP_RIGHT_PAD_X = 10.0F * Settings.scale;
    public static final float DECK_X= Settings.WIDTH - (ICON_W + TOP_RIGHT_PAD_X) * 2.0F;
    public static float deckAngle = 0.0F;

    public static CardGroup blasters;
    public static CardGroup shields;
    public static CardGroup cores;

    public static Hitbox blastersCheck;
    public static Hitbox shieldsCheck;
    public static Hitbox coresCheck;

    public static void init() {
        blasters = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        blastersCheck = new Hitbox(ICON_W, ICON_W);
        blastersCheck.move(DECK_X + ICON_W / 2.0F, ICON_Y + ICON_W / 2.0F + (ICON_W * 2));
        shields = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        shieldsCheck = new Hitbox(ICON_W, ICON_W);
        shieldsCheck.move(DECK_X + ICON_W / 2.0F, ICON_Y + ICON_W / 2.0F + (ICON_W * 3));
        cores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        coresCheck = new Hitbox(ICON_W, ICON_W);
        coresCheck.move(DECK_X + ICON_W / 2.0F, ICON_Y + ICON_W / 2.0F + (ICON_W * 4));
    }

    public static void update() {
        blastersCheck.update();
        shieldsCheck.update();
        coresCheck.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 2), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
        if (blastersCheck.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 2), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }

        blastersCheck.render(sb);

        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 3), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
        if (shieldsCheck.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 3), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }

        shieldsCheck.render(sb);

        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 4), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
        if (coresCheck.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 4), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }

        coresCheck.render(sb);
    }
}
