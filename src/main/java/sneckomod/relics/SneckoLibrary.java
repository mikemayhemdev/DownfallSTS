package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class SneckoLibrary extends CustomRelic {

    public static final String ID = HexaMod.makeID("SneckoLibrary");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpiritBrand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpiritBrand.png"));

    public SneckoLibrary() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }



    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
