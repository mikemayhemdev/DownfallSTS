package collector.Relics;

import automaton.AutomatonMod;
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
    private static final String IMG_PATH = "EmeraldTorch.png";
    private static final String OUTLINE_IMG_PATH ="EmeraldTorch.png";

    public EmeraldTorch() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);


    }
    public void atBattleStart() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(CollectorChar.torch, this));
        this.addToBot(new GainBlockAction(CollectorChar.torch,5));
    }
}

