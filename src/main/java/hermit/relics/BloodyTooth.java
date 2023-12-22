package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BloodyTooth extends CustomRelic {
    public static final String ID = HermitMod.makeID("BloodyTooth");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bloody_tooth.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bloody_tooth_outline.png"));

    public BloodyTooth() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void onVictory(){
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger)
        {
            this.flash();
            if (AbstractDungeon.player.currentHealth > 0) {
                AbstractDungeon.player.heal(7);
            }
            AbstractDungeon.player.gainGold(35);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
