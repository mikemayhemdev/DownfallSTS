package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.actions.GainReservesAction;
import collector.cards.Ember;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.TextureLoader;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public class EmeraldTorch extends CustomRelic {
    public static final String ID = CollectorMod.makeID("EmeraldTorch");
    private static final String IMG_PATH = "EmeraldTorch.png";
    private static final String OUTLINE_IMG_PATH = "EmeraldTorch.png";

    public EmeraldTorch() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);

    }

    @Override
    public void onVictory() {
        grayscale=false;
    }


    @Override
    public void atBattleStart() {
        System.out.println("DEBUG: This is a debug message.");
        grayscale=false;
        flash();
        makeInHand(new Ember(), 1);
  //      atb(new GainReservesAction(1));
    }


    @Override
    public void onExhaust(AbstractCard card) {
        if (!grayscale) {
            if (card.cardID.equals(Ember.ID)) {
                flash();
                atb(new GainReservesAction(1));
                grayscale=true;
            }
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

