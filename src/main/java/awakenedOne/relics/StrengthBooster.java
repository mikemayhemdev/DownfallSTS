package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class StrengthBooster extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("StrengthBooster");
    public static final int AMOUNT = 25;
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("StrengthBooster.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("StrengthBooster.png"));

    //Paper Crow / Paper Krow

    public StrengthBooster() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }
//
//    //Look at ApplyPowerPatch
//    public void onTrigger(int amount) {
//        if (amount > 0) {
//            this.flash();
//            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            vigor(AMOUNT);
//        }
//    }

    //debug
    public void onSpecificTrigger(int debug) {
        this.flash();
        System.out.println(debug);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
