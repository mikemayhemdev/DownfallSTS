package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeAction;
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

    @Override
    public void atBattleStart() {
        flash();
        int i = AbstractDungeon.cardRandomRng.random(GhostflameHelper.hexaGhostFlames.size() - 1);
        addToBot(new ChargeAction(GhostflameHelper.hexaGhostFlames.get(i)));
        grayscale = true;
    }
}
