package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class MatchstickCase extends CustomRelic {

    public static final String ID = HexaMod.makeID("MatchstickCase");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("UnbrokenSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("UnbrokenSoul.png"));

    public MatchstickCase() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    private boolean firstTurn = true;

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
            addToBot(new AdvanceAction());
            this.firstTurn = false;// 53
        }
    }
}
