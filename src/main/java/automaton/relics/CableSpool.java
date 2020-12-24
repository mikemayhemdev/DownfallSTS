package automaton.relics;

import automaton.AutomatonMod;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class CableSpool extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("CableSpool");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CableSpool.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CableSpool.png"));
    boolean activated = false;

    public CableSpool() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        activated = false;
    }

    @Override
    public void atTurnStart() {
        if (!activated) {
            beginLongPulse();
        }
    }

    @Override
    public void receiveCompile(AbstractCard q, boolean forGameplay) {
        if (!activated) {
            if (q.cost > 0) {
                q.cost -= 1;
                q.costForTurn -= 1;
            }
            if (forGameplay) {
                flash();
                activated = true;
                stopPulse();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
