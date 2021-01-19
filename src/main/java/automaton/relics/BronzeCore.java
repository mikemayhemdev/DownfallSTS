package automaton.relics;

import automaton.AutomatonMod;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class BronzeCore extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("BronzeCore");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeCore.png"));

    public BronzeCore() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    boolean activated = false;

    @Override
    public void atBattleStart() {
        activated = false;
        grayscale = false;
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (!activated) {
            if (function.cost > 0) {
                function.freeToPlayOnce = true;
                if (forGameplay) {
                    activated = true;
                    flash();
                    grayscale = true;
                }
            }
        }
    }

    @Override
    public void onVictory() {
        activated = false;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
