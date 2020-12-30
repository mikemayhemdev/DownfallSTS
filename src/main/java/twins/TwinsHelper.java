package twins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

public class TwinsHelper {

    public static float BACK_CARDS_LOCATION = Settings.WIDTH / 2F;
    public static float FRONT_CARDS_LOCATION = Settings.WIDTH / 4F;

    public static boolean donuInFront = false; // This is how to see if Donu or Deca is in front.

    public static CardGroup donuCards;
    public static CardGroup decaCards;

    public static CardGroup getFrontCardGroup() {
        if (donuInFront) {
            return donuCards;
        }
        return decaCards;
    }

    public static CardGroup getBackCardGroup() {
        if (donuInFront) {
            return decaCards;
        }
        return donuCards;
    }

    public static void init() {
        donuCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        decaCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }

    public static void update() {
        if (!donuCards.isEmpty()) {
            donuCards.getTopCard().update();
            donuCards.getTopCard().updateHoverLogic();
        }
        if (!decaCards.isEmpty()) {
            decaCards.getTopCard().update();
            decaCards.getTopCard().updateHoverLogic();
        }
    }

    public static void swap() {
        if (!getFrontCardGroup().isEmpty())
            getFrontCardGroup().getTopCard().target_x = BACK_CARDS_LOCATION;
        if (!getBackCardGroup().isEmpty())
            getBackCardGroup().getTopCard().target_x = FRONT_CARDS_LOCATION;
        donuInFront = !donuInFront;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (!donuCards.isEmpty())
            donuCards.getTopCard().render(sb);
        if (!decaCards.isEmpty())
            decaCards.getTopCard().render(sb);
    }
}
