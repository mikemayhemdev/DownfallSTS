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
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    //How much Block does this Relic give?
    private static final int BLOCK = 4;

    @Override
    public void onPlayerEndTurn() {
        if (FunctionHelper.doStuff) {
            if (FunctionHelper.isSequenceEmpty()) {
                flash();
                addToTop(new GainBlockAction(AbstractDungeon.player, BLOCK));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }

}
