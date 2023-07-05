package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import collector.actions.DrawCardFromCollectionAction;
import com.badlogic.gdx.graphics.Texture;

import static collector.util.Wiz.atb;

public class BagOfTricks extends CustomRelic {
    public static final String ID = CollectorMod.makeID(BagOfTricks.class.getSimpleName());
    private static final String IMG_PATH = BagOfTricks.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = BagOfTricks.class.getSimpleName() + ".png";

    private static final int EXTRA_CARDS = 2;

    public BagOfTricks() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        for (int i = 0; i < EXTRA_CARDS; i++) {
            atb(new DrawCardFromCollectionAction());
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EXTRA_CARDS + DESCRIPTIONS[1];
    }
}

