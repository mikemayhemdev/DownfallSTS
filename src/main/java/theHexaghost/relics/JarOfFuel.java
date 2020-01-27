package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;
import theHexaghost.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class JarOfFuel extends CustomRelic {

    public static final String ID = HexaMod.makeID("JarOfFuel");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AncientSpiritShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AncientSpiritShield.png"));

    public JarOfFuel() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractGhostflame q = GhostflameHelper.hexaGhostFlames.get(0);
        AbstractGhostflame gf = new BolsteringGhostflame(q.lx, q.ly);
        AbstractGhostflame r = new SearingGhostflame(gf.lx, gf.ly);
        GhostflameHelper.hexaGhostFlames.set(0, gf);
        GhostflameHelper.hexaGhostFlames.set(3, r);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
