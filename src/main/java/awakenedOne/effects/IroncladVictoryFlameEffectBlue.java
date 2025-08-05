package awakenedOne.effects;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import awakenedOne.AwakenedOneMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class IroncladVictoryFlameEffectBlue extends AbstractGameEffect {
    private float x;
    private float y;
    private final float vX;
    private final float vY;
    private final boolean flipX = MathUtils.randomBoolean();
    private final TextureAtlas.AtlasRegion img;

    public IroncladVictoryFlameEffectBlue() {
        this.duration = 1.0F;
        this.startingDuration = this.duration;
        this.renderBehind = MathUtils.randomBoolean();
        switch (MathUtils.random(2)) {
            case 0:
                this.img = ImageMaster.FLAME_1;
                break;
            case 1:
                this.img = ImageMaster.FLAME_2;
                break;
            default:
                this.img = ImageMaster.FLAME_3;
        }

        this.x = MathUtils.random(600.0F, 1320.0F) * Settings.xScale - (float) this.img.packedWidth / 2.0F;
        this.y = -300.0F * Settings.scale - (float) this.img.packedHeight / 2.0F;
        if (this.x > 960.0F * Settings.xScale) {
            this.vX = MathUtils.random(0.0F, -120.0F) * Settings.xScale;
        } else {
            this.vX = MathUtils.random(120.0F, 0.0F) * Settings.xScale;
        }

        this.vY = MathUtils.random(600.0F, 800.0F) * Settings.scale;
        this.color = AwakenedOneMod.placeholderColor;
        this.renderBehind = false;
        this.scale = MathUtils.random(6.0F, 7.0F) * Settings.scale;
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.color.a = Interpolation.pow3Out.apply(0.0F, 0.8F, this.duration / this.startingDuration);
        this.duration -= Gdx.graphics.getDeltaTime();
        this.scale += Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        if (this.flipX && !this.img.isFlipX()) {
            this.img.flip(true, false);
        } else if (!this.flipX && this.img.isFlipX()) {
            this.img.flip(true, false);
        }

        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
