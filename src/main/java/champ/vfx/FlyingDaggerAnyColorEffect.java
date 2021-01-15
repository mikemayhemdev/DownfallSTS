package champ.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class FlyingDaggerAnyColorEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private float scaleMultiplier;
    private static final float DUR = 0.5F;
    private TextureAtlas.AtlasRegion img;
    private boolean playedSound = false;

    public FlyingDaggerAnyColorEffect(float x, float y, float fAngle, boolean shouldFlip, Color clr) {
        this.img = com.megacrit.cardcrawl.helpers.ImageMaster.DAGGER_STREAK;
        this.x = (x - this.img.packedWidth / 2.0F);
        this.destY = y;
        this.y = (this.destY - this.img.packedHeight / 2.0F);
        this.startingDuration = 0.5F;
        this.duration = 0.5F;
        this.scaleMultiplier = MathUtils.random(1.2F, 1.5F);
        this.scale = (0.25F * com.megacrit.cardcrawl.core.Settings.scale);

        if (shouldFlip) {
            this.rotation = (fAngle - 180.0F);
        } else {
            this.rotation = fAngle;
        }

        this.color = clr.cpy();
        this.color.a = 0.0F;
    }

    private void playRandomSfX() {
        int roll = MathUtils.random(5);
        switch (roll) {
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
            playRandomSfX();
            this.playedSound = true;
        }

        this.duration -= Gdx.graphics.getDeltaTime();


        Vector2 derp = new Vector2(MathUtils.cos(0.017453292F * this.rotation), MathUtils.sin(0.017453292F * this.rotation));

        this.x += derp.x * Gdx.graphics.getDeltaTime() * (3500.0F * this.scaleMultiplier) * com.megacrit.cardcrawl.core.Settings.scale;
        this.y += derp.y * Gdx.graphics.getDeltaTime() * (3500.0F * this.scaleMultiplier) * com.megacrit.cardcrawl.core.Settings.scale;

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        if (this.duration > 0.25F) {
            this.color.a = com.badlogic.gdx.math.Interpolation.pow5In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
        } else {
            this.color.a = com.badlogic.gdx.math.Interpolation.fade.apply(0.0F, 1.0F, this.duration * 4.0F);
        }

        this.scale += Gdx.graphics.getDeltaTime() * this.scaleMultiplier;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale * 1.5F, this.rotation);


        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * 0.75F, this.scale * 0.75F, this.rotation);


        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
