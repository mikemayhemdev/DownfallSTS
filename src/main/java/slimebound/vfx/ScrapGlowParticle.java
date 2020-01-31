//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import slimebound.orbs.ScrapOozeSlime;

public class ScrapGlowParticle extends AbstractGameEffect {
    private static float xOffset = -25 * Settings.scale;
    private static float yOffset = -28 * Settings.scale;
    public ScrapOozeSlime p;
    private float x;
    private float y;
    private AtlasRegion img;
    private boolean activated = false;
    private boolean reverse;
    private int W;

    public ScrapGlowParticle(ScrapOozeSlime p, Color color) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.scale = 0.006F;
        this.startingDuration = 0.5F;
        this.W = img.packedWidth;
        this.duration = this.startingDuration;
        this.color = color;
        this.activated = true;
        this.p = p;
        this.renderBehind = false;
    }

    public void finish() {
        this.isDone = true;

    }

    public void update() {

        if (reverse) {
            this.scale = Interpolation.linear.apply(.15F, .2F, this.duration - .5F);
        } else {
            this.scale = Interpolation.linear.apply(.15F, .2F, .5F - this.duration);

        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0F) {
            reverse = !reverse;
            this.duration = 0.5F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(this.img, this.p.x + xOffset, this.p.y + yOffset, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.p.x + xOffset, this.p.y + yOffset, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
    }


    public void dispose() {
        this.isDone = true;
    }
}
