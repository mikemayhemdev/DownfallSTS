package awakenedOne.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ReverseAwakenedWingParticle extends AbstractGameEffect {
    private float x;
    private float y;
    private float tScale;
    private final TextureAtlas.AtlasRegion img;

    public ReverseAwakenedWingParticle() {
        this.duration = 2.0F;
        this.startingDuration = this.duration;
        this.img = ImageMaster.THICK_3D_LINE_2;
        this.scale = 0.01F;
        this.rotation = -MathUtils.random(25.0F, 85.0F);
        this.renderBehind = MathUtils.randomBoolean(0.2F);
        if (this.renderBehind) {
            this.tScale = MathUtils.random(0.8F, 1.2F);
        }

        this.color = new Color(0.3F, 0.3F, MathUtils.random(0.3F, 0.35F), MathUtils.random(0.5F, 0.9F));
        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            this.x = MathUtils.random(-340.0F, -170.0F) * Settings.scale;
            this.y = MathUtils.random(-20.0F, 20.0F) * Settings.scale;
            this.tScale = MathUtils.random(0.4F, 0.5F);
        } else if (roll == 1) {
            this.x = MathUtils.random(-220.0F, -20.0F) * Settings.scale;
            this.y = MathUtils.random(-40.0F, -10.0F) * Settings.scale;
            this.tScale = MathUtils.random(0.4F, 0.5F);
        } else {
            this.x = MathUtils.random(-270.0F, -60.0F) * Settings.scale;
            this.y = MathUtils.random(-30.0F, -0.0F) * Settings.scale;
            this.tScale = MathUtils.random(0.4F, 0.7F);
        }

        this.x += 155.0F * Settings.scale;
        this.y += 30.0F * Settings.scale;
        this.x -= (float) (this.img.packedWidth / 2);
        this.y -= (float) (this.img.packedHeight / 2);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration > 1.0F) {
            this.scale = Interpolation.bounceIn.apply(this.tScale, 0.01F, this.duration - 1.0F) * Settings.scale;
        }

        if (this.duration < 0.2F) {
            this.color.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration * 5.0F);
        }

    }

    public void render(SpriteBatch sb) {
        //this.img.flip(true, false);

    }

    public void render(SpriteBatch sb, float x, float y) {

        //      if (!this.img.isFlipX()) {
        //            this.img.flip(true, false);
        //        }

        float derp = MathUtils.random(3.0F, 5.0F);

        sb.setColor(new Color(0.4F, 1.0F, 1.0F, this.color.a / 2.0F));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x + x, this.y + y, (float) this.img.packedWidth * 0.08F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, (this.scale * MathUtils.random(1.1F, 1.25F)), this.scale, this.rotation + derp + 180.0F);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.color);
        sb.draw(this.img, this.x + x, this.y + y, (float) this.img.packedWidth * 0.08F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation + derp + 180.0F);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, this.color.a / 5.0F));
        sb.draw(this.img, this.x + x, this.y + y, (float) this.img.packedWidth * 0.08F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, (this.scale * 0.7F), this.scale * 0.7F, this.rotation + derp - 40.0F + 180.0F);
    }

    public void dispose() {
    }
}
