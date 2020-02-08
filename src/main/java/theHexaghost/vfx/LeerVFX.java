package theHexaghost.vfx;

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

public class LeerVFX extends AbstractGameEffect {
    private static AtlasRegion img;
    private final float DUR = 0.5F;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float mX;
    private float mY;
    private float dst;
    private boolean isFlipped = false;

    public LeerVFX(float sX, float sY, float mXs, float mYs, boolean isFlipped) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }

        this.mX = mXs;
        this.mY = mYs;

        this.isFlipped = isFlipped;
        if (isFlipped) {
            this.sX = sX - 32.0F * Settings.scale;
            this.sY = sY + 20.0F * Settings.scale;
        } else {
            this.sX = sX + 40.0F * Settings.scale;
            this.sY = sY + 50.0F * Settings.scale;
        }

        this.color = Color.RED.cpy();
        this.duration = 0.5F;
        this.startingDuration = 0.5F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.isFlipped) {
            this.dX = mX;
            this.dY = mY;
        } else {
            this.dX = mX;
            this.dY = mY;
        }

        this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        this.rotation = MathUtils.atan2(this.dX - this.sX, this.dY - this.sY);
        this.rotation *= 57.295776F;
        this.rotation = -this.rotation + 90.0F;
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
        } else {
            this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration * 4.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.sX, this.sY - (float) img.packedHeight / 2.0F + 10.0F * Settings.scale, 0.0F, (float) img.packedHeight / 2.0F, this.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, this.rotation);
        sb.setColor(new Color(Color.RED.r, Color.RED.b, Color.RED.g, color.a));
        sb.draw(img, this.sX, this.sY - (float) img.packedHeight / 2.0F, 0.0F, (float) img.packedHeight / 2.0F, this.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
