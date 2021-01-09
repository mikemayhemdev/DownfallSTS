package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class HeatCrushEffect extends AbstractGameEffect {
    private static AtlasRegion img;
    private boolean impactHook = false;
    private float x;
    private float x2;
    private float y;
    private float startX;
    private float startX2;
    private float targetX;
    private float targetX2;

    public HeatCrushEffect(float x, float y) {
        if (img == null) {// 24
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");// 25
        }

        this.startX = x - 300.0F * Settings.scale - (float) img.packedWidth / 2.0F;// 28
        this.startX2 = x + 300.0F * Settings.scale - (float) img.packedWidth / 2.0F;// 29
        this.targetX = x - 120.0F * Settings.scale - (float) img.packedWidth / 2.0F;// 30
        this.targetX2 = x + 120.0F * Settings.scale - (float) img.packedWidth / 2.0F;// 31
        this.x = this.startX;// 32
        this.x2 = this.startX2;// 33
        this.y = y - (float) img.packedHeight / 2.0F;// 35
        this.scale = 1.1F;// 36
        this.duration = 0.7F;// 37
        this.startingDuration = 0.7F;// 38
        this.rotation = 90.0F;// 39
        this.color = Color.RED.cpy();// 40
        this.color.a = 0.0F;// 41
    }// 42

    public void update() {
        if (this.duration == this.startingDuration) {// 45
            CardCrawlGame.sound.playA("ATTACK_MAGIC_FAST_3", -0.4F);// 46
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 49
        if (this.duration < 0.0F) {// 51
            this.isDone = true;// 52
        } else if (this.duration < 0.2F) {// 53
            if (!this.impactHook) {// 54
                this.impactHook = true;// 55
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED.cpy()));// 56
                CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, true);// 57
            }

            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 5.0F);// 59
        } else {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration / this.startingDuration);// 61
        }

        this.scale += 1.1F * Gdx.graphics.getDeltaTime();// 64
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.duration / this.startingDuration);// 65
        this.x2 = Interpolation.fade.apply(this.targetX2, this.startX2, this.duration / this.startingDuration);// 66
    }// 67

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 71
        sb.setColor(new Color(0.5F, 0.5F, 0.9F, this.color.a));// 74
        sb.draw(img, this.x, this.y, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, this.scale * this.scale * Settings.scale * 0.5F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation);// 75
        sb.draw(img, this.x2, this.y, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, this.scale * this.scale * Settings.scale * 0.5F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation - 180.0F);// 87
        sb.setColor(new Color(0.7F, 0.5F, 0.9F, this.color.a));// 100
        sb.draw(img, this.x, this.y, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, this.scale * this.scale * Settings.scale * 0.75F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation);// 101
        sb.draw(img, this.x2, this.y, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, this.scale * this.scale * Settings.scale * 0.75F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation - 180.0F);// 112
        sb.setColor(this.color);// 125
        sb.draw(img, this.x, this.y, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, this.scale * this.scale * Settings.scale, this.scale * Settings.scale * (this.duration + 1.0F), this.rotation);// 126
        sb.draw(img, this.x2, this.y, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, this.scale * this.scale * Settings.scale, this.scale * Settings.scale * (this.duration + 1.0F), this.rotation - 180.0F);// 138
        sb.setBlendFunction(770, 771);// 149
    }// 150

    public void dispose() {
    }// 155
}
