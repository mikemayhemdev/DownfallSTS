package twins.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import twins.DonuDecaMod;
import twins.util.TextureLoader;

import static twins.DonuDecaMod.makeRelicOutlinePath;
import static twins.DonuDecaMod.makeRelicPath;

public class Rings extends CustomRelic {

    public static final String ID = DonuDecaMod.makeID("Rings");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Ring.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Ring.png"));

    public Rings() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
