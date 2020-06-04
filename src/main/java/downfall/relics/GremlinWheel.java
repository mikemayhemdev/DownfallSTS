package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import downfall.downfallMod;

public class GremlinWheel extends CustomRelic {

    public static final String ID = downfallMod.makeID("GremlinWheel");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/GremlinWheel.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/GremlinWheel.png"));

    public GremlinWheel() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0) {
            this.usedUp();
        }
    }

}
