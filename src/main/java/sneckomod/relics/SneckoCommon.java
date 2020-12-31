package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import sneckomod.SneckoMod;
import theHexaghost.util.TextureLoader;

public class SneckoCommon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoCommon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoCommon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoCommon.png"));

    public SneckoCommon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
