package automaton.relics;

import automaton.AutomatonMod;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class BronzeIdol extends CustomRelic  {

    public static final String ID = AutomatonMod.makeID("BronzeIdol");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeIdol.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeIdol.png"));

    public BronzeIdol() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
