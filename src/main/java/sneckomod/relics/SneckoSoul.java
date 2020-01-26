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

public class SneckoSoul extends CustomRelic {

    public static final String ID = HexaMod.makeID("SneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpiritBrand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpiritBrand.png"));

    public SneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c instanceof AbstractUnknownCard) {
            AbstractDungeon.player.increaseMaxHp(1, false);
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
