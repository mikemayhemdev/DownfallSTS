package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;

public class TickHelperRelic extends CustomRelic {
    public static final String ID = "Guardian:TickHelperRelic";
    public static final String IMG_PATH = "relics/coolingFluid.png";
    public static final String OUTLINE_IMG_PATH = "relics/coolingFluidOutline.png";
    public static final String LARGE_IMG_PATH = "relics/coolingFluidLarge.png";

    public TickHelperRelic() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new TickHelperRelic();
    }
}