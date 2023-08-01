package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.cards.Ember;
import collector.util.EssenceSystem;
import downfall.util.TextureLoader;

import static collector.util.Wiz.makeInHand;

public class SoullitLamp extends CustomRelic {
    public static final String ID = CollectorMod.makeID(SoullitLamp.class.getSimpleName());
    private static final String IMG_PATH = SoullitLamp.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = SoullitLamp.class.getSimpleName() + ".png";

    public SoullitLamp() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
        tips.add(new CardPowerTip(new Ember()));
    }

    @Override
    public void atBattleStart() {
        flash();
        makeInHand(new Ember());
    }

    @Override
    public void onEquip() {
        EssenceSystem.changeEssence(3);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

