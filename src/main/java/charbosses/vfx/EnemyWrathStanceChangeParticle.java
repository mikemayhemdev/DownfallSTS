package charbosses.vfx;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EnemyWrathStanceChangeParticle extends AbstractGameEffect {
    private AtlasRegion img;
    private float x;
    private float y;
    private float delayTimer;

    public EnemyWrathStanceChangeParticle(float playerX) {
        this.img = ImageMaster.STRIKE_LINE;
        this.startingDuration = 1.0F;
        this.duration = this.startingDuration;
        this.color = new Color(1.0F, MathUtils.random(0.1F, 0.3F), 0.1F, 0.0F);
        this.x = MathUtils.random(-30.0F, 30.0F) * Settings.scale - (float)this.img.packedWidth / 2.0F;
        this.y = (float)Settings.HEIGHT / 2.0F + MathUtils.random(-150.0F, 150.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.scale = MathUtils.random(2.2F, 2.5F) * Settings.scale;
        this.delayTimer = MathUtils.random(0.5F);
        this.rotation = MathUtils.random(89.0F, 91.0F);
        this.renderBehind = MathUtils.randomBoolean(0.9F);
    }

    public void update() {
        if (this.delayTimer > 0.0F) {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        } else {
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            } else {
                if (this.duration > this.startingDuration / 2.0F) {
                    this.color.a = Interpolation.pow3In.apply(0.6F, 0.0F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
                } else {
                    this.color.a = Interpolation.fade.apply(0.0F, 0.6F, this.duration / (this.startingDuration / 2.0F));
                }

            }
        }
    }

    public void render(SpriteBatch sb) {
        if (this.delayTimer <= 0.0F) {
            sb.setColor(this.color);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, AbstractCharBoss.boss.hb.cX + this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(2.9F, 3.1F), this.scale * MathUtils.random(0.95F, 1.05F), this.rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }
}
