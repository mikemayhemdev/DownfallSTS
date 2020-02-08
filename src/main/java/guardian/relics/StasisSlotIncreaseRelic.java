package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;

public class StasisSlotIncreaseRelic extends CustomRelic {
    public static final String ID = "Guardian:StasisSlotIncreaseRelic";
    public static final String IMG_PATH = "relics/policeBox.png";
    public static final String OUTLINE_IMG_PATH = "relics/policeBoxOutline.png";
    public static final String LARGE_IMG_PATH = "relics/policeBoxLarge.png";
    private static final int HP_PER_CARD = 1;

    public StasisSlotIncreaseRelic() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.UNCOMMON, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    public void onEquip() {

        ++AbstractDungeon.player.masterMaxOrbs;
        ++AbstractDungeon.player.masterMaxOrbs;
    }

    public void onUnequip() {
        --AbstractDungeon.player.masterMaxOrbs;
        --AbstractDungeon.player.masterMaxOrbs;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new StasisSlotIncreaseRelic();
    }
}