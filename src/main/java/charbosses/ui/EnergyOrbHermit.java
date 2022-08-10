package charbosses.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class EnergyOrbHermit implements EnergyOrbInterface {
    private static final int ORB_W = 128;
    public static float fontScale = 1.0F;
    private static final float ORB_IMG_SCALE;
    private float angle5;
    private float angle4;
    private float angle3;
    private float angle2;
    private float angle1;

    private static Texture ENERGY_HERMIT_LAYER1 = loadImage("hermitResources/images/char/hermit/orb/layer1.png");
    private static Texture ENERGY_HERMIT_LAYER2 = loadImage("hermitResources/images/char/hermit/orb/layer2.png");
    private static Texture ENERGY_HERMIT_LAYER3 = loadImage("hermitResources/images/char/hermit/orb/layer3.png");
    private static Texture ENERGY_HERMIT_LAYER4 = loadImage("hermitResources/images/char/hermit/orb/layer4.png");
    private static Texture ENERGY_HERMIT_LAYER5 = loadImage("hermitResources/images/char/hermit/orb/layer5.png");
    private static Texture ENERGY_HERMIT_LAYER6 = loadImage("hermitResources/images/char/hermit/orb/layer6.png");

    private static Texture ENERGY_HERMIT_LAYER1D = loadImage("hermitResources/images/char/hermit/orb/layer1d.png");
    private static Texture ENERGY_HERMIT_LAYER2D = loadImage("hermitResources/images/char/hermit/orb/layer2d.png");
    private static Texture ENERGY_HERMIT_LAYER3D = loadImage("hermitResources/images/char/hermit/orb/layer3d.png");
    private static Texture ENERGY_HERMIT_LAYER4D = loadImage("hermitResources/images/char/hermit/orb/layer4d.png");
    private static Texture ENERGY_HERMIT_LAYER5D = loadImage("hermitResources/images/char/hermit/orb/layer5d.png");
    public EnergyOrbHermit() {
    }

    public void updateOrb(int orbCount) {
        if (orbCount == 0) {
            this.angle5 += Gdx.graphics.getDeltaTime() * -5.0F;
            this.angle4 += Gdx.graphics.getDeltaTime() * 5.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -8.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 8.0F;
            this.angle1 += Gdx.graphics.getDeltaTime() * 72.0F;
        } else {
            this.angle5 += Gdx.graphics.getDeltaTime() * -20.0F;
            this.angle4 += Gdx.graphics.getDeltaTime() * 20.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -40.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 40.0F;
            this.angle1 += Gdx.graphics.getDeltaTime() * 360.0F;
        }

    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        if (enabled) {
            sb.setColor(Color.WHITE);
            sb.draw(this.ENERGY_HERMIT_LAYER1, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER2, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER3, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER4, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER5, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle5, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER6, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
        } else {
            sb.setColor(Color.WHITE);
            sb.draw(this.ENERGY_HERMIT_LAYER1D, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER2D, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER3D, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER4D, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER5D, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle5, 0, 0, 128, 128, false, false);
            sb.draw(this.ENERGY_HERMIT_LAYER6, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
        }

    }

    static {
        ORB_IMG_SCALE = 1.15F * Settings.scale;
    }
}
