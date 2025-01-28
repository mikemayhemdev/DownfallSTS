package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import downfall.actions.OctoChoiceAction;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;
import theHexaghost.cards.MatchstickFloat;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import theHexaghost.cards.Float;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class MatchstickCase extends CustomRelic implements OnChargeSubscriber {
    //Sneaky Teakwood Match
    public static final String ID = HexaMod.makeID("MatchstickCase");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MatchstickCase.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MatchstickCase.png"));
    private boolean triggered = false;

    public MatchstickCase() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        triggered = false;
        this.grayscale = false;
        super.atTurnStart();
    }

    @Override
    public void onCharge(AbstractGhostflame chargedFlame) {
        if(!triggered) {
            MatchstickFloat fl = new MatchstickFloat();
            fl.upgrade();
            addToBot(new OctoChoiceAction(null, fl));
            triggered = true;
            this.grayscale = true;
        }
    }

    //
//    public void atTurnStart() {
//        if (this.firstTurn) {
//            this.flash();
//            addToBot(new ChargeCurrentFlameAction());
//            addToBot(new AdvanceAction(false));
//            this.firstTurn = false;
//        }
//    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
