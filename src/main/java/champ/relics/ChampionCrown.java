package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.util.OnOpenerSubscriber;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampionCrown extends CustomRelic implements OnOpenerSubscriber {

    public static final String ID = ChampMod.makeID("ChampionCrown");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));

    public ChampionCrown() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onOpener(boolean fromNeutral) {
        if (fromNeutral) {
            flash();
            addToBot(new OpenerReduceCostAction());
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
