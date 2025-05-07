package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class DefensiveTrainingManual extends CustomRelic {

    public static final String ID = ChampMod.makeID("DefensiveTrainingManual");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DefensiveManual.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DefensiveManual.png"));

    //extra Block gain
    public static final int OOMPH = 3;

    //DefensiveStance.java line 85
    public DefensiveTrainingManual() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + OOMPH + DESCRIPTIONS[1];
    }

}
