package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import downfall.downfallMod;

public class TeleportStone extends CustomRelic {

    public static final String ID = downfallMod.makeID("TeleportStone");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/TeleportStone.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/TeleportStone.png"));

    public TeleportStone() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onTrigger() {
        this.setCounter(0);
    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0) {
            this.usedUp();
        }

    }
}
