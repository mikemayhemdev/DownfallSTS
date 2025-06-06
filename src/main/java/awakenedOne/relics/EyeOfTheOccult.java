package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

import static awakenedOne.AwakenedOneMod.*;

public class EyeOfTheOccult extends CustomRelic {

    //Eye of the Occult

    public static final String ID = AwakenedOneMod.makeID("EyeOfTheOccult");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("EyeOfTheOccult.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("EyeOfTheOccult.png"));

    //Yeah... you need to go look at the Spell cards for this one.
    public EyeOfTheOccult() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
