package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class IceCube extends CustomRelic {

    public static final String ID = HexaMod.makeID("IceCube");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("IceCube.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("IceCube.png"));

    public IceCube() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;// 37
    }// 38

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;// 42
    }// 43

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
