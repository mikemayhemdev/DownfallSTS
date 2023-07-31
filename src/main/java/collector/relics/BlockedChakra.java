package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

public class BlockedChakra extends CustomRelic {
    public static final String ID = CollectorMod.makeID(BlockedChakra.class.getSimpleName());
    private static final String IMG_PATH = BlockedChakra.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = BlockedChakra.class.getSimpleName() + ".png";

    public BlockedChakra() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
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
    public boolean canSpawn() {
        return !CollectorCollection.collection.isEmpty();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

