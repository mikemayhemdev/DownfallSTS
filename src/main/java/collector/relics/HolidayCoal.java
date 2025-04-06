package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.cards.collectibles.LuckyWick;
import downfall.util.TextureLoader;

public class HolidayCoal extends CustomRelic {
    public static final String ID = CollectorMod.makeID(HolidayCoal.class.getSimpleName());
    private static final String IMG_PATH = HolidayCoal.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = HolidayCoal.class.getSimpleName() + ".png";

    public HolidayCoal() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.SHOP, LandingSound.MAGICAL);
        tips.add(new CardPowerTip(new LuckyWick()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

