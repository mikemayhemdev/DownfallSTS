package slimebound.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import slimebound.orbs.CultistSlime;


public class SticksParticleRight extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = 0.75F;
    private static final float START_SCALE = 1.2F * Settings.scale;
    private static int W;
    private static int xOffset = 0;
    private static int yOffset = -28;
    public CultistSlime p;
    private float scale = 1F;
    private Texture img;

    public SticksParticleRight(CultistSlime p) {
        this.duration = 0.05F;
        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/cultistrightstick.png");
        W = img.getWidth();
        this.p = p;
        this.renderBehind = true;


    }

    public void update() {


    }

    public void finish() {
        this.isDone = true;

    }

    public void dispose() {
        this.isDone = true;
        this.img.dispose();
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(1F, 1F, 1F, 2F));

        sb.draw(this.img, this.p.attachmentXr + this.p.animX + this.p.cX - W / 2.0F + (xOffset * Settings.scale), this.p.attachmentYr + p.animY + this.p.cY - W / 2.0F + ((yOffset) * Settings.scale), W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);


    }
}


