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

public class SpookyEmberEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float startDur;
    private float targetScale;
    private AtlasRegion img;
    private float rotateSpeed = 0.0F;

    public SpookyEmberEffect(float x, float y) {
        switch (MathUtils.random(2)) {
            case 0:
                this.color = Color.PURPLE.cpy();
                break;
            case 1:
                this.color = Color.PINK.cpy();
                break;
            case 2:
                this.color = Color.DARK_GRAY.cpy();
        }

        this.duration = MathUtils.random(0.6F, 1.4F);
        this.duration *= this.duration;
        this.targetScale = MathUtils.random(0.4F, 0.8F);
        this.startDur = this.duration;
        this.vX = MathUtils.random(-150.0F * Settings.scale, 150.0F * Settings.scale);
        this.vY = MathUtils.random(-100.0F * Settings.scale, 300.0F * Settings.scale);
        this.x = x + MathUtils.random(-170.0F * Settings.scale, 170.0F * Settings.scale);
        this.y = y + MathUtils.random(-220.0F * Settings.scale, 150.0F * Settings.scale);
        this.scale = 0.01F;
        this.img = this.setImg();
        this.rotateSpeed = MathUtils.random(-700.0F, 700.0F);
    }

    private AtlasRegion setImg() {
        switch (MathUtils.random(0, 5)) {
            case 0:
                return ImageMaster.DUST_1;
            case 1:
                return ImageMaster.DUST_2;
            case 2:
                return ImageMaster.DUST_3;
            case 3:
                return ImageMaster.DUST_4;
            case 4:
                return ImageMaster.DUST_5;
            default:
                return ImageMaster.DUST_6;
        }
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.rotation += this.rotateSpeed * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.swing.apply(0.01F, this.targetScale, 1.0F - this.duration / this.startDur);
        if (this.duration < 0.5F) {
            this.color.a = this.duration * 2.0F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.offsetX, this.img.offsetY, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setColor(new Color(this.color.r, this.color.g, this.color.b, this.color.a / 3.0F));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, this.img.offsetX, this.img.offsetY, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
