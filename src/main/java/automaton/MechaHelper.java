package automaton;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;

public class MechaHelper {
    public static final float ICON_W = 64.0F * Settings.scale;
    public static final float ICON_Y = Settings.isMobile ? (float)Settings.HEIGHT - ICON_W - 12.0F * Settings.scale : (float)Settings.HEIGHT - ICON_W;
    public static final float TOP_RIGHT_PAD_X = 10.0F * Settings.scale;
    public static final float DECK_X= Settings.WIDTH - (ICON_W + TOP_RIGHT_PAD_X) * 2.0F;

    public static CardGroup blasters;
    public static CardGroup shields;
    public static CardGroup cores;

    public static Hitbox blastersCheck;
    public static Hitbox shieldsCheck;
    public static Hitbox coresCheck;

    public static void init() {
        blasters = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        blastersCheck = new Hitbox(ICON_W, ICON_W);
        blastersCheck.move(DECK_X + ICON_W / 2.0F, ICON_Y + ICON_W / 2.0F);
        shields = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }


}
