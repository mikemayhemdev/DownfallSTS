package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import theHexaghost.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class SpiritBrand extends CustomRelic implements OnChargeSubscriber {

    public static final String ID = HexaMod.makeID("SpiritBrand");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpiritBrand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpiritBrand.png"));

    public SpiritBrand() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onCharge(AbstractGhostflame g) {
        flash();
        addToBot(new GainBlockAction(AbstractDungeon.player, 3));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
