package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AfterlifeBlurEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private float aV;
    private AtlasRegion img;
    private float startDur;
    private float targetScale;

    public AfterlifeBlurEffect(float x, float y) {
        this.color = new Color(0.4F, 0.16F, 0.5F, 1F);
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.EXHAUST_L;
            this.duration = MathUtils.random(0.9F, 1.2F);
            this.targetScale = MathUtils.random(0.5F, 1.3F);
        } else {
            this.img = ImageMaster.EXHAUST_S;
            this.duration = MathUtils.random(0.6F, 1.4F);
            this.targetScale = MathUtils.random(0.3F, 1.0F);
        }

        this.startDur = this.duration;
        this.x = x + MathUtils.random(-150.0F * Settings.scale, 150.0F * Settings.scale) - (float)this.img.packedWidth / 2.0F;
        this.y = y + MathUtils.random(-240.0F * Settings.scale, 150.0F * Settings.scale) - (float)this.img.packedHeight / 2.0F;
        this.scale = 0.01F;
        this.rotation = MathUtils.random(360.0F);
        this.aV = MathUtils.random(-250.0F, 250.0F);
        this.vY = MathUtils.random(1.0F * Settings.scale, 5.0F * Settings.scale);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.y += this.vY * Gdx.graphics.getDeltaTime() * 60.0F;
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.swing.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);
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
