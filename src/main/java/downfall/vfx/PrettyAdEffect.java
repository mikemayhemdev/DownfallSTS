package downfall.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PrettyAdEffect extends AbstractGameEffect {
    private float effectDuration;
    private float x;
    private float y;
    private float vY;
    private float alpha;
    private float targetScale;
    private TextureAtlas.AtlasRegion img;

    public PrettyAdEffect(float x, float y) {
        this.img = ImageMaster.ROOM_SHINE_2;
        this.effectDuration = MathUtils.random(1.0F, 3.0F);
        this.duration = this.effectDuration;
        this.startingDuration = this.effectDuration;
        this.x = x;
        this.y = y;
        this.vY = MathUtils.random(10.0F, 50.0F) * Settings.scale;
        this.alpha = MathUtils.random(0.7F, 1.0F);
        this.color = new Color(1.0F, 1.0F, MathUtils.random(0.6F, 0.9F), this.alpha);
        this.scale = 0.01F;
        this.targetScale = MathUtils.random(0.5F, 1.2F);
        this.rotation = MathUtils.random(-3.0F, 3.0F);
    }

    public void update() {
        if (this.vY != 0.0F) {
            this.y += this.vY * Gdx.graphics.getDeltaTime();
            MathUtils.lerp(this.vY, 0.0F, Gdx.graphics.getDeltaTime() * 10.0F);
            if (this.vY < 0.5F) {
                this.vY = 0.0F;
            }
        }

        float t = (this.effectDuration - this.duration) * 2.0F;
        if (t > 1.0F) {
            t = 1.0F;
        }

        float tmp = Interpolation.bounceOut.apply(0.01F, this.targetScale, t);
        this.scale = tmp * tmp * Settings.scale;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < this.effectDuration / 2.0F) {
            this.color.a = Interpolation.exp5In.apply(0.0F, this.alpha, this.duration / (this.effectDuration / 2.0F));
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(0.9F, 1.1F), this.scale * MathUtils.random(0.7F, 1.3F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
