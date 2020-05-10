package downfall.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;

public class ThrowGoldEffect extends AbstractGameEffect
{
    private static final float GRAVITY = 0;//2000.0F * Settings.scale;
    private static final float START_VY = 800.0F * Settings.scale;
    private static final float START_VY_JITTER = 400.0F * Settings.scale;
    private static final float START_VX = -200.0F * Settings.scale;
    private static final float START_VX_JITTER = 200.0F * Settings.scale;
    private static final float TARGET_JITTER = 10.0F * Settings.scale;
    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float targetX;
    private float targetY;
    private float finalTargetX;
    private float finalTargetY;
    private TextureAtlas.AtlasRegion img;
    private float alpha = 0.0F;
    private float suctionTimer = 1.4F;
    private float staggerTimer;
    private float EPSILON = 0.0001F;
    private boolean hitTarget = false;

    public ThrowGoldEffect(AbstractCreature owner, float x, float y, float targetX, float targetY, float staggerTimer)
    {
        if (MathUtils.randomBoolean()) {
            img = ImageMaster.COPPER_COIN_1;
        } else {
            img = ImageMaster.COPPER_COIN_2;
        }
        this.x = x - img.packedWidth / 2.0F;
        this.y = y - img.packedHeight / 2.0F;
        this.targetX = targetX + MathUtils.random(-TARGET_JITTER, TARGET_JITTER);
        this.targetY = targetY + MathUtils.random(-TARGET_JITTER, TARGET_JITTER);

        this.staggerTimer = staggerTimer;
        this.vX = MathUtils.random(START_VX, START_VX_JITTER);
        this.rotationSpeed = MathUtils.random(500.0F, 2000.0F);
        if (MathUtils.randomBoolean()) {
            this.rotationSpeed *= -1;
        }
        this.vY = MathUtils.random(START_VY, START_VY_JITTER);
        this.scale = Settings.scale;
        this.color = new Color(1.0F,1.0F,1.0F,0.0F);
    }

    public ThrowGoldEffect(AbstractCreature owner, float x, float y, float targetX, float targetY)
    {
        this(owner, x, y, targetX, targetY, MathUtils.random(0.0F, 0.5F));
    }

    @Override
    public void update()
    {
        /*
        if (staggerTimer > 0.0F) {
            staggerTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        //*/

        if (alpha != 1.0F) {
            alpha += Gdx.graphics.getDeltaTime() * 2.0F;
            if (alpha > 1.0F) {
                alpha = 1.0F;
            }
            color.a = alpha;
        }
        rotation += Gdx.graphics.getDeltaTime() * rotationSpeed;
        x += Gdx.graphics.getDeltaTime() * vX;
        y += Gdx.graphics.getDeltaTime() * vY;
        vY -= Gdx.graphics.getDeltaTime() * GRAVITY;

        if (suctionTimer > 0.0F) {
            suctionTimer -= Gdx.graphics.getDeltaTime();

            vY = MathUtils.lerp(vY, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            vX = MathUtils.lerp(vX, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);

            finalTargetX = ((targetX - x) * 2.0F) + x;
            finalTargetY = ((targetY - y) * 2.0F) + y;
        } else {
            if (staggerTimer > 0.0F) {
                staggerTimer -= Gdx.graphics.getDeltaTime();
                return;
            }

            this.x = MathUtils.lerp(this.x, this.finalTargetX, Gdx.graphics.getDeltaTime() * 4.0F);
            this.y = MathUtils.lerp(this.y, this.finalTargetY, Gdx.graphics.getDeltaTime() * 4.0F);
            if (!hitTarget && Math.abs(this.x - this.targetX) < 20.0F) {
                hitTarget = true;
                if (MathUtils.randomBoolean()) {
                    CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
                }

                AbstractDungeon.effectsQueue.add(new ShineLinesEffect(this.x, this.y));
            }

            if (this.x < 0.0F) {
                this.isDone = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {
        /*
        if (this.staggerTimer > 0.0F) {
            return;
        }
        //*/
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose()
    {

    }
}