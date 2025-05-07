package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class SpiritBrand extends CustomRelic implements OnChargeSubscriber {

    public static final String ID = HexaMod.makeID("SpiritBrand");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpiritBrand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpiritBrand.png"));

    public boolean activated = false;

    public SpiritBrand() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    //variables
    private static final int BLOCK = 4;

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStartPostDraw() {
        activated = false;
        beginPulse();
    }

    @Override
    public void onCharge(AbstractGhostflame g) {
        if (!activated) {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK));
            activated = true;
            stopPulse();
        }
    }
}
