package automaton.relics;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ProtectiveGoggles extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("ProtectiveGoggles");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Barbell.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Barbell.png"));

    public ProtectiveGoggles() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (FunctionHelper.doStuff) {
            if (FunctionHelper.held.isEmpty()) {
                flash();
                addToTop(new GainBlockAction(AbstractDungeon.player, 8));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
