package guardian.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import guardian.GuardianMod;

public abstract class AbstractGuardianPower extends AbstractPower {

    /**
     * @param bigImageName - is the name of the 84x84 image for your power.
     * @param smallImageName - is the name of the 32x32 image for your power.
     */
    public void setImage(String bigImageName, String smallImageName){
        String path = GuardianMod.getResourcePath();

        String path128 = path + "powers/" + bigImageName;
        String path48 = path + "powers/" + smallImageName;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    /**
     * @param imgName - is the name of a 16x16 image. Example: setTinyImage("power.png");
     */
    public void setTinyImage(String imgName){
        String path = GuardianMod.getResourcePath("powers/");
        this.img = ImageMaster.loadImage(path + imgName);
    }
}
