package slimebound.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import slimebound.orbs.ScrapOozeSlime;


public class ScrapParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = 0.75F;
    private static final float START_SCALE = 1.2F * Settings.scale;
    private static int W;
    private static int xOffset = 20;
    private static int yOffset = 20;
    public ScrapOozeSlime p;
    private float scale = 1F;
    private Texture img;

    public ScrapParticle(ScrapOozeSlime p) {
        this.duration = 0.05F;
        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/scrap.png");
        W = img.getWidth();
        this.p = p;
        this.renderBehind = false;


    }

    public void finish() {
        this.isDone = true;

    }

    public void update() {


    }

    public void dispose() {
        this.img.dispose();
        this.isDone = true;
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(.8F, .8F, .8F, 2F));

        sb.draw(this.img, this.p.attachmentX + p.animX + this.p.cX - W / 2.0F + ((xOffset) * Settings.scale), this.p.attachmentY + this.p.animY + this.p.cY - W / 2.0F + ((yOffset) * Settings.scale), W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);


    }
}


