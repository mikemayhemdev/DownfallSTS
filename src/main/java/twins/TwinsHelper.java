package twins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TwinsHelper {
    public static final float ICON_W = 64.0F * Settings.scale;
    public static final float ICON_Y = Settings.isMobile ? (float) Settings.HEIGHT - ICON_W - 12.0F * Settings.scale : (float) Settings.HEIGHT - ICON_W;
    public static final float TOP_RIGHT_PAD_X = 10.0F * Settings.scale;
    public static final float DECK_X = Settings.WIDTH - (ICON_W + TOP_RIGHT_PAD_X) * 2.0F;
    public static float deckAngle = 0.0F;

    public static boolean donuInFront = false; //TODO: The code for DragonTamer's extra-player-character is super complex and not even released.
    //Making a secondary character for Donu/Deca is difficult, but totally necessary. For now this boolean can handle it

    public static CardGroup donuCards;
    public static CardGroup decaCards;

    public static Hitbox donuCardsHitbox;
    public static Hitbox decaCardsHitbox;

    public static void init() {
        donuCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        donuCardsHitbox = new Hitbox(ICON_W, ICON_W);
        donuCardsHitbox.move(DECK_X + ICON_W / 2.0F, ICON_Y + ICON_W / 2.0F + (ICON_W * 2));
        decaCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        decaCardsHitbox = new Hitbox(ICON_W, ICON_W);
        decaCardsHitbox.move(DECK_X + ICON_W / 2.0F, ICON_Y + ICON_W / 2.0F + (ICON_W * 3));
    }

    public static void update() {
        donuCardsHitbox.update();
        decaCardsHitbox.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 2), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
        if (donuCardsHitbox.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 2), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }

        donuCardsHitbox.render(sb);

        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 3), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
        if (decaCardsHitbox.hovered) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(ImageMaster.DECK_ICON, DECK_X - 32.0F + 32.0F * Settings.scale, ICON_Y - 32.0F + 32.0F * Settings.scale + (ICON_W * 3), 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, this.deckAngle, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }

        decaCardsHitbox.render(sb);
    }
}
