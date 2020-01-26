//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

public class LeechEffectParticle extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private AtlasRegion img;
    private boolean activated = false;

    public LeechEffectParticle(float sX, float sY, float tX, float tY, Color color) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX + MathUtils.random(-30.0F, 30.0F) * Settings.scale;
        this.sY = sY + MathUtils.random(-30.0F, 30.0F) * Settings.scale;
        this.tX = tX + MathUtils.random(-50.0F, 50.0F) * Settings.scale;
        this.tY = tY + MathUtils.random(-50.0F, 50.0F) * Settings.scale;
        this.vX = this.sX + MathUtils.random(-100.0F, 100.0F) * Settings.scale;
        this.vY = this.sY + MathUtils.random(-100.0F, 100.0F) * Settings.scale;
        this.x = this.sX;
        this.y = this.sY;
        this.scale = 0.006F;
        this.startingDuration = 0.8F;
        this.duration = this.startingDuration;
        this.renderBehind = MathUtils.randomBoolean(0.2F);
        this.color = color;
        this.activated = true;
        this.sX = this.x;
        this.sY = this.y;
        this.duration = this.startingDuration/2;
    }

    public void update() {

            this.scale = Interpolation.pow3Out.apply(2.0F, 2.5F, this.duration / (this.startingDuration / 2.0F)) * Settings.scale;
            this.x = Interpolation.pow2Out.apply(this.tX, this.vX, this.duration / (this.startingDuration / 2.0F));
            this.y = Interpolation.swingOut.apply(this.tY, this.vY, this.duration / (this.startingDuration / 2.0F));
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
        this.isDone = true;
    }
}
