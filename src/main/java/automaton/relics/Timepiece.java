package automaton.relics;

import automaton.AutomatonMod;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import downfall.cardmods.RetainCardMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class Timepiece extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("Timepiece");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Timepiece.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Timepiece.png"));

    public Timepiece() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new RetainCardMod());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
