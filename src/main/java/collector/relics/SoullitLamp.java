package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import collector.powers.ReservePower;
import com.badlogic.gdx.graphics.Texture;

import static collector.util.Wiz.applyToSelf;

public class SoullitLamp extends CustomRelic {
    public static final String ID = CollectorMod.makeID(SoullitLamp.class.getSimpleName());
    private static final String IMG_PATH = SoullitLamp.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = SoullitLamp.class.getSimpleName() + ".png";

    public SoullitLamp() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        applyToSelf(new ReservePower(1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

