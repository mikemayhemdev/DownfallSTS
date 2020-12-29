package automaton.relics;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class ElectromagneticCoil extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("ElectromagneticCoil");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ElectromagneticCoil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ElectromagneticCoil.png"));

    public ElectromagneticCoil() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        FunctionHelper.max = 4;
        AutomatonMod.compileDisplayPanel.x += (135 * Settings.scale); //
    }

    @Override
    public void onUnequip() {
        FunctionHelper.max = 3;
        AutomatonMod.compileDisplayPanel.x -= (135 * Settings.scale); //
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
