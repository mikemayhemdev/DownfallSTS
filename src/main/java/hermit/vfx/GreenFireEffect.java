package hermit.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GreenFireEffect extends AbstractGameEffect {
    private AtlasRegion img;
    private float brightness;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float startingDuration;
    private boolean flipX = MathUtils.randomBoolean();
    private float delayTimer = MathUtils.random(0.1F);

    public GreenFireEffect() {
        this.setImg();
        this.startingDuration = 1.5F;
        this.duration = this.startingDuration;
        this.x = MathUtils.random(0.0F, (float)Settings.WIDTH) - (float)this.img.packedWidth / 2.0F;
        this.y = MathUtils.random(-200.0F, -400.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.vX = MathUtils.random(-70.0F, 70.0F) * Settings.scale;
        this.vY = MathUtils.random(500.0F, 1700.0F) * Settings.scale;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        Color var10000 = this.color;
        var10000.r -= MathUtils.random(0.5F);
        var10000 = this.color;
        var10000.b -= this.color.g - MathUtils.random(0.0F, 0.2F);
        this.rotation = MathUtils.random(-10.0F, 10.0F);
        this.scale = MathUtils.random(0.5F, 7.0F);
        this.brightness = MathUtils.random(0.2F, 0.6F);
    }

    public void update() {
        if (this.delayTimer > 0.0F) {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        } else {
            this.x += this.vX * Gdx.graphics.getDeltaTime();
            this.y += this.vY * Gdx.graphics.getDeltaTime();
            this.scale *= MathUtils.random(0.95F, 1.05F);
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            } else if (this.startingDuration - this.duration < 0.75F) {
                this.color.a = Interpolation.fade.apply(0.0F, this.brightness, (this.startingDuration - this.duration) / 0.75F);
            } else if (this.duration < 1.0F) {
                this.color.a = Interpolation.fade.apply(0.0F, this.brightness, this.duration / 1.0F);
            }

        }
    }

    private void setImg() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            this.img = ImageMaster.FLAME_1;
        } else if (roll == 1) {
            this.img = ImageMaster.FLAME_2;
        } else {
            this.img = ImageMaster.FLAME_3;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if (this.flipX && !this.img.isFlipX()) {
            this.img.flip(true, false);
        } else if (!this.flipX && this.img.isFlipX()) {
            this.img.flip(true, false);
        }

        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}