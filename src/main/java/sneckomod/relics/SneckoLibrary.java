package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import sneckomod.SneckoMod;
import theHexaghost.util.TextureLoader;

import static sneckomod.SneckoMod.makeRelicPath;

public class SneckoLibrary extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoLibrary");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicPath("SneckoSoulOutline.png"));

    public SneckoLibrary() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
