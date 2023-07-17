package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import downfall.util.TextureLoader;

public class BlockedChakra extends CustomRelic {
    public static final String ID = CollectorMod.makeID(BlockedChakra.class.getSimpleName());
    private static final String IMG_PATH = BlockedChakra.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = BlockedChakra.class.getSimpleName() + ".png";

    public BlockedChakra() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        counter = 4;
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

