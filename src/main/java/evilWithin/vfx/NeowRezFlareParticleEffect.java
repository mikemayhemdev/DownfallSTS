package evilWithin.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class NeowRezFlareParticleEffect extends AbstractGameEffect {
    private Vector2 pos = new Vector2();
    private float speed;
    private float speedStart;
    private float speedTarget;
    private AtlasRegion img;

    public NeowRezFlareParticleEffect(float x, float y, Color color, float angle, NeowBossRezEffect owner) {
        this.img = ImageMaster.STRIKE_BLUR;
        this.duration = MathUtils.random(0.2F, .5F);
        this.startingDuration = this.duration;
        this.pos.x = x - (float)this.img.packedWidth / 2.0F;
        this.pos.y = y - (float)this.img.packedHeight / 2.0F;
        this.speed = MathUtils.random(100F,800.0F) * Settings.scale;
        this.speedStart = this.speed;
        this.speedTarget = MathUtils.random(.7F, 1.3F) * this.speed;
        this.color = color.cpy();
        this.color.a = 0.0F;
        this.renderBehind = false;
        this.rotation = MathUtils.random(-360F, 360.0F);
        this.scale = MathUtils.random(0.2F, 1.0F) * Settings.scale;
    }

    public void update() {
        Vector2 tmp = new Vector2(MathUtils.cosDeg(this.rotation), MathUtils.sinDeg(this.rotation));
        tmp.x *= this.speed * Gdx.graphics.getDeltaTime();
        tmp.y *= this.speed * Gdx.graphics.getDeltaTime();
        this.speed = Interpolation.pow2OutInverse.apply(this.speedStart, this.speedTarget, 1.0F - this.duration / this.startingDuration);
        this.pos.x += tmp.x;
        this.pos.y += tmp.y;
        //this.rotation += MathUtils.cos(this.duration * this.waveSpeed) * this.waveIntensity;
        if (this.duration < 0.5F) {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 2.0F);
        } else {
            this.color.a = 1.0F;
        }

        super.update();
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(this.color.r, this.color.g, this.color.b, this.color.a / 4.0F));
        sb.draw(this.img, this.pos.x, this.pos.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 4.0F, this.scale * 4.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.pos.x, this.pos.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
