package automaton.relics;

import automaton.AutomatonMod;
import basemod.abstracts.CustomRelic;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.TextureLoader;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class Timepiece extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("Timepiece");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("coolingFluid.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("coolingFluid.png"));

    public Timepiece() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new RetainMod());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
