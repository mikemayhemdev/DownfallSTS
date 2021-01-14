package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import downfall.util.TextureLoader;

public class SneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoSoul.png"));

    public SneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
