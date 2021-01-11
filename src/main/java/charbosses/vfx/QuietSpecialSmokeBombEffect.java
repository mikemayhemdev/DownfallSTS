package charbosses.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class QuietSpecialSmokeBombEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float aV;
    private float startDur;
    private float targetScale;
    private AtlasRegion img;

    public QuietSpecialSmokeBombEffect(float x, float y) {
        this.color = new Color(0.0F, 0.0F, 0.0F, 1.0F);
        this.color.r = MathUtils.random(0.5F, 0.6F);
        this.color.g = this.color.r + MathUtils.random(0.0F, 0.2F);
        this.color.b = 0.2F;
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.EXHAUST_L;
        } else {
            this.img = ImageMaster.EXHAUST_S;
        }
        this.duration = MathUtils.random(2.0F, 2.5F);
        this.targetScale = MathUtils.random(0.2F, 0.3F);

        this.startDur = this.duration;
        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y - (float)this.img.packedHeight / 2.0F;
        this.scale = 0.01F;
        this.rotation = MathUtils.random(360.0F);
        this.aV = MathUtils.random(-250.0F, 250.0F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.exp10Out.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);
        if (this.duration < 0.33F) {
            this.color.a = this.duration * 3.0F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
