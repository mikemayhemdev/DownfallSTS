package collector.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MiniHammerImprintEffect extends AbstractGameEffect {
    private final TextureAtlas.AtlasRegion img;
    private static final float DUR = 0.7F;
    private final float x;
    private final float y;
    private float hammerGlowScale;
    private final Color shineColor;

    public MiniHammerImprintEffect(float x, float y) {
        this.img = ImageMaster.UPGRADE_HAMMER_IMPACT;
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.x = x - (float) (this.img.packedWidth / 2);
        this.y = y - (float) (this.img.packedHeight / 2);
        this.color = Color.WHITE.cpy();
        this.color.a = 0.7F;
        this.duration = 0.7F;
        this.scale = Settings.scale / 5F;
        this.rotation = MathUtils.random(0.0F, 360.0F);
        this.hammerGlowScale = 1.0F - this.duration;
        this.hammerGlowScale *= this.hammerGlowScale;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.color.a = this.duration;
        this.hammerGlowScale = 0.3F - this.duration;
        this.hammerGlowScale *= this.hammerGlowScale * this.hammerGlowScale;
        this.scale += Gdx.graphics.getDeltaTime() / 20.0F;
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x + MathUtils.random(-2.0F, 2.0F) * Settings.scale, this.y + MathUtils.random(-2.0F, 2.0F) * Settings.scale, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        this.shineColor.a = this.color.a / 10.0F;
        sb.setColor(this.shineColor);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.hammerGlowScale, this.hammerGlowScale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
