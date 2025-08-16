package hermit.effects;

import charbosses.bosses.Hermit.CharBossHermit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import hermit.characters.hermit;

public class HermitEyeParticle extends AbstractGameEffect {
    private float x;
    private float y;
    private final AtlasRegion img;
    public AbstractCreature parent;
    public Skeleton skeleton;
    private hermit hermitParent;
    private CharBossHermit hermitBossParent;

    public HermitEyeParticle(float x, float y, AbstractCreature parent, Skeleton skeleton) {
        this.duration = MathUtils.random(0.5F, 1.0F);
        this.startingDuration = this.duration;
        this.img = ImageMaster.ROOM_SHINE_2;
        this.x = x - (float)(this.img.packedWidth / 2);
        this.y = y - (float)(this.img.packedHeight / 2);
        this.scale = Settings.scale * MathUtils.random(0.5F, 1.0F);
        this.rotation = 0.0F;
        this.color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.2F, 0.4F), MathUtils.random(0.2F, 0.4F), 0.01F);

        this.parent = parent;
        this.skeleton = skeleton;

        if (parent instanceof hermit){
            hermitParent = (hermit)parent;
        } else if (parent instanceof CharBossHermit) {
            hermitBossParent = (CharBossHermit) parent;
        }
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        if (hermitParent != null) {
            this.x = skeleton.getX() + hermitParent.eye.getBone().getWorldX() - (float) (this.img.packedWidth / 2.07);
            this.y = skeleton.getY() + hermitParent.eye.getBone().getWorldY() - (float) (this.img.packedHeight / 2.07);
        }
        else if (hermitBossParent != null) {
            this.x = skeleton.getX() + hermitBossParent.eye.getBone().getWorldX() - (float) (this.img.packedWidth / 2.07);
            this.y = skeleton.getY() + hermitBossParent.eye.getBone().getWorldY() - (float) (this.img.packedHeight / 2.07);
        }

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