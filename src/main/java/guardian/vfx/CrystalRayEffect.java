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
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class CrystalRayEffect extends AbstractGameEffect {
    private static final float DUR = 0.5F;
    private AtlasRegion img;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float dst;
    private ArrayList<GuardianMod.socketTypes> gems;

    public CrystalRayEffect(float sX, float sY, float dX, float dY, ArrayList<GuardianMod.socketTypes> gems) {
        this.img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        this.gems = gems;

        this.sX = sX;
        this.sY = sY;
        this.dX = dX;
        this.dY = dY;
        this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        this.color = Color.WHITE.cpy();
        this.duration = .8F;
        this.startingDuration = .8F;
        this.rotation = MathUtils.atan2(dX - sX, dY - sY);
        this.rotation *= 180F / Math.PI;
        this.rotation = -this.rotation + 90.0F;
    }

    public void update() {
        Color a, b;
        this.duration -= Gdx.graphics.getDeltaTime();
        int steps = 1;

        if(this.gems.size() > 2) {
            int idx = (int) ((this.duration / this.startingDuration) * (this.gems.size() - 1));
            if (idx >= this.gems.size() - 1) idx -= 1;
            a = this.gems.get(idx).color;
            b = this.gems.get(idx + 1).color;
            steps = this.gems.size() - 1;
        } else if (this.gems.size() == 2) {
            a = this.gems.get(0).color;
            b = this.gems.get(1).color;
        } else if (this.gems.size() == 1) {
            a = b = this.gems.get(0).color;
        } else {
            a = b = Color.CYAN;
        }
        float dt = this.gems.size() == 0 ? this.startingDuration : this.startingDuration / steps;
        float t = this.gems.size() == 0 ? this.duration : this.duration % dt;
        this.color.r = Interpolation.pow2.apply(a.r, b.r, t / dt);
        this.color.g = Interpolation.pow2.apply(a.g, b.g, t / dt);
        this.color.b = Interpolation.pow2.apply(a.b, b.b, t / dt);
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 8.0F);
        } else {
            this.color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, this.duration * 8.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        float scaleV = 0.8f + 0.1f * Math.min(this.gems.size(), 10);
        sb.setColor(Color.LIGHT_GRAY);
        sb.draw(img, this.sX, this.sY - (float) img.packedHeight / 2.0F + 10.0F * Settings.scale, 0.0F, (float) img.packedHeight / 2.0F, this.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, this.rotation);
        sb.setColor(this.color);
        sb.draw(img, this.sX, this.sY - (float) img.packedHeight / 2.0F, 0.0F, (float) img.packedHeight / 2.0F, this.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale * scaleV, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
