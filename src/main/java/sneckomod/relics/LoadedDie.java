package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

public class LoadedDie extends CustomRelic {

    public static final String ID = SneckoMod.makeID("LoadedDie");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("LoadedDie.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("LoadedDie.png"));

    public LoadedDie() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
