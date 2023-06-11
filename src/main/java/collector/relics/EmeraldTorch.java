package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.cards.Ember;
import com.badlogic.gdx.graphics.Texture;

import static collector.util.Wiz.makeInHand;

public class EmeraldTorch extends CustomRelic {
    public static final String ID = CollectorMod.makeID("EmeraldTorch");
    private static final String IMG_PATH = "EmeraldTorch.png";
    private static final String OUTLINE_IMG_PATH = "EmeraldTorch.png";

    public EmeraldTorch() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);
        this.tips.add(new CardPowerTip(new Ember())); //TODO: check if this works
    }

    @Override
    public void atBattleStart() {
        flash();
        makeInHand(new Ember());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

