package automaton.relics;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class ProtectiveGoggles extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("ProtectiveGoggles");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ProtectiveGoggles.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ProtectiveGoggles.png"));

    public ProtectiveGoggles() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (FunctionHelper.doStuff) {
            if (FunctionHelper.isSequenceEmpty()) {
                flash();
                addToTop(new GainBlockAction(AbstractDungeon.player, 4));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
