package automaton.relics;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class ElectromagneticCoil extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("ElectromagneticCoil");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ElectromagneticCoil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ElectromagneticCoil.png"));

    public ElectromagneticCoil() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ++FunctionHelper.max;
    }

    @Override
    public void onUnequip() {
        --FunctionHelper.max;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
