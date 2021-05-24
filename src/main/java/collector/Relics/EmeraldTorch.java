package collector.Relics;

import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import collector.CollectorChar;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

import static collector.CollectorMod.makeRelicOutlinePath;
import static collector.CollectorMod.makeRelicPath;

public class EmeraldTorch extends CustomRelic {
    public static final String ID = CollectorMod.makeID("EmeraldTorch");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("EmeraldTorch.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmeraldTorch.png"));

    public EmeraldTorch() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);

    }
    public void atBattleStart() {
        this.flash();
        this.addToBot(new GainBlockAction(CollectorChar.torch,5));
        this.addToTop(new RelicAboveCreatureAction(CollectorChar.torch, this));
    }
}

