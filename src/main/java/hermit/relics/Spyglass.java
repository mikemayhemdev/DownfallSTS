package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Spyglass extends CustomRelic {
    public static final String ID = HermitMod.makeID("Spyglass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("spyglass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("spyglass.png"));

    public Spyglass() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
