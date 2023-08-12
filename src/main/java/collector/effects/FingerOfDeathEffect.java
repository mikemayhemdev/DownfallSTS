package collector.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class FingerOfDeathEffect extends AbstractGameEffect {
    private float x, y;
    private static final float DUR = 1f;
    private static TextureAtlas.AtlasRegion img;
    private boolean playedSfx = false;
    private boolean flipHorizontal;
    private Color color2;

    public FingerOfDeathEffect(float x, float y, boolean flipHorizontal) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThick");
        }

        this.flipHorizontal = flipHorizontal;
        this.x = x;
        this.y = y;

        color = Color.valueOf("d4b926");
        color2 = Color.valueOf("d48c26");
        duration = DUR;
        startingDuration = DUR;
    }

    @Override
    public void update() {
        if (!playedSfx) {
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SKY));
            playedSfx = true;
            CardCrawlGame.sound.play("ATTACK_MAGIC_FAST_2");
            CardCrawlGame.screenShake.rumble(2f);
        }
        duration -= Gdx.graphics.getDeltaTime();

        if (duration > startingDuration / 2f) {
            color.a = Interpolation.pow2In.apply(1f, 0f, (duration - DUR / 2f));
        } else {
            color.a = Interpolation.pow2Out.apply(0f, 1f, duration);
        }

        if (duration < 0f) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.setColor(color2);

        if (!flipHorizontal) {
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f + MathUtils.random(-0.05f, 0.05f), scale * 1.5f + MathUtils.random(-0.1f, 0.1f), MathUtils.random(-4f, 4f));
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f + MathUtils.random(-0.05f, 0.05f), scale * 1.5f + MathUtils.random(-0.1f, 0.1f), MathUtils.random(-4f, 4f));
            sb.setColor(color);
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f, scale / 2f, MathUtils.random(-2f, 2f));
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f, scale / 2f, MathUtils.random(-2f, 2f));
        } else {
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f + MathUtils.random(-0.05f, 0.05f), scale * 1.5f + MathUtils.random(-0.1f, 0.1f), MathUtils.random(186f, 189f));
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f + MathUtils.random(-0.05f, 0.05f), scale * 1.5f + MathUtils.random(-0.1f, 0.1f), MathUtils.random(186f, 189f));
            sb.setColor(color);
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f, scale / 2f, MathUtils.random(187f, 188f));
            sb.draw(img, x, y - img.packedHeight / 2f, 0f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale * 2f, scale / 2f, MathUtils.random(187f, 188f));
        }
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose() {
    }
}