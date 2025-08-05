package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BlackPowder extends CustomRelic {
    public static final String ID = HermitMod.makeID("BlackPowder");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("black_powder.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("black_powder_outline.png"));

    //aoe damage dealt
    public static final int OOMPH = 2;

    public BlackPowder() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + OOMPH + DESCRIPTIONS[1];
    }
}