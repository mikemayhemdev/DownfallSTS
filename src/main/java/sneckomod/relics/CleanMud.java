package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.actions.RemoveRandomDebuffAction;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

public class CleanMud extends CustomRelic {

    public static final String ID = SneckoMod.makeID("CleanMud");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("CleanMud.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("CleanMud.png"));

    public CleanMud() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onTrigger() {this.flash();}

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
