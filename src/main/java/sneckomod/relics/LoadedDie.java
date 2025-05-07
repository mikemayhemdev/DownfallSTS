package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

public class LoadedDie extends CustomRelic {

    public static final String ID = SneckoMod.makeID("LoadedDie");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("LoadedDie.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("LoadedDie.png"));

    //todo: set variable for block gain (use in muddleaction / muddlemarkedaction)

    public LoadedDie() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public void onTrigger() {this.flash();}

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
