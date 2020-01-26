//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class IronWaveParticleBlue extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private float x;
    private float y;
    private float targetY;
    private static AtlasRegion img;
    private boolean impactHook = false;

    public IronWaveParticleBlue(float x, float y) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");
        }

        this.scale = Settings.scale;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = (float)Settings.HEIGHT - (float)img.packedHeight / 2.0F;
        this.duration = 0.5F;
        this.targetY = y - 180.0F * Settings.scale;
        this.rotation = 0.0F;
        this.color = new Color(0.4F, 0.4F, 1F, 0.0F);
    }

    public void update() {
        this.y = Interpolation.fade.apply((float)Settings.HEIGHT, this.targetY, 1.0F - this.duration / 0.5F);
        this.scale += Gdx.graphics.getDeltaTime();
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < 0.2F) {
            if (!this.impactHook) {
                this.impactHook = true;
                CardCrawlGame.screenShake.shake(ShakeIntensity.LOW, ShakeDur.SHORT, false);
            }

            this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration * 5.0F);
        } else {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration / 0.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        this.color.g = 1.0F;
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight * (this.duration + 0.2F) * 3.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.5F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);
        this.color.g = 0.7F;
        sb.setColor(this.color);
        sb.draw(img, this.x - 50.0F * Settings.scale, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight * (this.duration + 0.2F) * 2.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.6F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);
        this.color.g = 0.5F;
        sb.setColor(this.color);
        sb.draw(img, this.x - 100.0F * Settings.scale, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight * (this.duration + 0.2F) * 1.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.75F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
