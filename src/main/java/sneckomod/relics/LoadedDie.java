package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

public class LoadedDie extends CustomRelic {

    public static final String ID = SneckoMod.makeID("LoadedDie");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("LoadedDie.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("LoadedDie.png"));

    //variables
    private static final int BLOCK = 1;

    public LoadedDie() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public void onTrigger() {
        this.flash();

        addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }
}
