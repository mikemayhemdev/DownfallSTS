package hermit.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import hermit.characters.hermit;

public class HermitEyeParticle extends AbstractGameEffect {
    private float x;
    private float y;
    private AtlasRegion img;
    public hermit parent;
    public Skeleton skeleton;

    public HermitEyeParticle(float x, float y) {
        this.duration = MathUtils.random(0.5F, 1.0F);
        this.startingDuration = this.duration;
        this.img = ImageMaster.ROOM_SHINE_2;
        this.x = x - (float)(this.img.packedWidth / 2);
        this.y = y - (float)(this.img.packedHeight / 2);
        this.scale = Settings.scale * MathUtils.random(0.5F, 1.0F);
        this.rotation = 0.0F;
        this.color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.2F, 0.4F), MathUtils.random(0.2F, 0.4F), 0.01F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.x = skeleton.getX() + parent.eye.getBone().getWorldX() - (float)(this.img.packedWidth / 2.07);
        this.y = skeleton.getY() + parent.eye.getBone().getWorldY() - (float)(this.img.packedHeight / 2.07);
        this.color.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration / this.startingDuration);
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(6.0F, 12.0F), this.scale * MathUtils.random(0.7F, 0.8F), this.rotation + MathUtils.random(-1.0F, 1.0F));
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(0.2F, 0.5F), this.scale * MathUtils.random(2.0F, 3.0F), this.rotation + MathUtils.random(-1.0F, 1.0F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}