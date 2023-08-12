package collector.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GreenThirdEyeParticleEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private float vY;
    private TextureAtlas.AtlasRegion img;

    public GreenThirdEyeParticleEffect(float x, float y, float vX, float vY) {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            this.img = ImageMaster.FLAME_1;
        } else if (roll == 1) {
            this.img = ImageMaster.FLAME_3;
        } else {
            this.img = ImageMaster.FLAME_3;
        }

        this.x = x - (float) this.img.packedWidth / 2.0F;
        this.y = y - (float) this.img.packedHeight / 2.0F;
        this.vX = vX * Settings.scale;
        this.vY = vY * Settings.scale;
        this.rotation = (new Vector2(vX, vY)).angle() - 90.0F;
        this.scale = 3.0F * Settings.scale;
        this.color = Color.CHARTREUSE.cpy();
        this.color.a = 0.0F;
        this.startingDuration = 0.6F;
        this.duration = this.startingDuration;
        this.renderBehind = true;
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2Out.apply(0.7F, 0.0F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration / (this.startingDuration / 2.0F));
            this.scale = Interpolation.fade.apply(0.01F, 3.0F, this.duration / (this.startingDuration / 2.0F)) * Settings.scale;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale + MathUtils.random(-0.05F, 0.05F), this.scale + MathUtils.random(-0.05F, 0.05F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}