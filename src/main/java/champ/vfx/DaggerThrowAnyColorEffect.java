//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DaggerThrowAnyColorEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private static final float DUR = 0.4F;
    private AtlasRegion img;
    private boolean playedSound = false;
    private boolean forcedAngle = false;

    public DaggerThrowAnyColorEffect(float x, float y, Color clr) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - MathUtils.random(320.0F, 360.0F) - (float)this.img.packedWidth / 2.0F;
        this.destY = y;
        this.y = this.destY + MathUtils.random(-25.0F, 25.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = MathUtils.random(-3.0F, 3.0F);
        this.color = clr.cpy();
}


    public DaggerThrowAnyColorEffect(float x, float y, Color clr, float fAngleStart, float fAngleEnd) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - MathUtils.random(320.0F, 360.0F) - (float)this.img.packedWidth / 2.0F;
        this.destY = y;
        this.y = this.destY + MathUtils.random(-25.0F, 25.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = MathUtils.random(fAngleStart, fAngleEnd);
        this.color = clr.cpy();
    }

    public DaggerThrowAnyColorEffect(float x, float y, Color clr, float fAngle) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - MathUtils.random(320.0F, 360.0F) - (float)this.img.packedWidth / 2.0F;
        this.destY = y;
        this.y = this.destY + MathUtils.random(-25.0F, 25.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = fAngle;
        this.color = clr.cpy();
        this.forcedAngle = true;
    }

    public DaggerThrowAnyColorEffect(float x, float y, Color clr, float fAngle, boolean flip, boolean behind, boolean mute, float size) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - MathUtils.random(320.0F, 360.0F) - (float)this.img.packedWidth / 2.0F;
        this.destY = y;
        this.y = this.destY + MathUtils.random(-25.0F, 25.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = fAngle;
        this.color = clr.cpy();
        this.forcedAngle = true;
        this.renderBehind = behind;
        playedSound = mute;
        scale = size;
        if (flip) {
            this.rotation = fAngle - 180.0F;
        } else {
            this.rotation = fAngle;
        }
    }

    private void playRandomSfX() {
        int roll = MathUtils.random(5);
        switch(roll) {
            case 0:
                CardCrawlGame.sound.play("ATTACK_DAGGER_1");
                break;
            case 1:
                CardCrawlGame.sound.play("ATTACK_DAGGER_2");
                break;
            case 2:
                CardCrawlGame.sound.play("ATTACK_DAGGER_3");
                break;
            case 3:
                CardCrawlGame.sound.play("ATTACK_DAGGER_4");
                break;
            case 4:
                CardCrawlGame.sound.play("ATTACK_DAGGER_5");
                break;
            default:
                CardCrawlGame.sound.play("ATTACK_DAGGER_6");
        }

    }

    public void update() {
        if (!this.playedSound) {
            this.playRandomSfX();
            this.playedSound = true;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration > 0.2F) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 0.2F) * 5.0F);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 5.0F);
        }

        this.scale = Interpolation.bounceIn.apply(Settings.scale * 0.5F, Settings.scale * 1.5F, this.duration / 0.4F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        if (!this.forcedAngle) {
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth * 0.85F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale * 1.5F, this.rotation);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth * 0.85F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.75F, this.scale * 0.75F, this.rotation);
            sb.setBlendFunction(770, 771);
        } else {
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale * 1.5F, this.rotation);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.75F, this.scale * 0.75F, this.rotation);
            sb.setBlendFunction(770, 771);
        }

    }

    public void dispose() {
    }
}
