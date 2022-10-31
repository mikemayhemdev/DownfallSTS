package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeCurrentFlameAction;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class MatchstickCase extends CustomRelic {

    public static final String ID = HexaMod.makeID("MatchstickCase");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MatchstickCase.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MatchstickCase.png"));
    private boolean firstTurn = true;

    public MatchstickCase() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void atPreBattle() {
        this.firstTurn = true;// 44
    }// 45

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
    }

    public void atTurnStart() {
        if (this.firstTurn) {// 49
            this.flash();// 50
            addToBot(new ChargeCurrentFlameAction());
            addToBot(new AdvanceAction(false));
            this.firstTurn = false;// 53
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
