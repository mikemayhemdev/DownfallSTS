package downfall.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;

import java.util.ArrayList;

public class SoulStealParticle extends AbstractGameEffect {
    private AtlasRegion img;
    private CatmullRomSpline<Vector2> crs = new CatmullRomSpline();
    private ArrayList<Vector2> controlPoints = new ArrayList();
    private static final int TRAIL_ACCURACY = 60;
    private Vector2[] points = new Vector2[60];
    private Vector2 pos;
    private Vector2 target;
    private float currentSpeed = 0.0F;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise = true;
    private boolean stopRotating = false;
    private boolean facingLeft;
    private float rotationRate;

    public SoulStealParticle(float sX, float sY, float tX, float tY, boolean facingLeft) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.pos = new Vector2(sX, sY);
        if (!facingLeft) {
            this.target = new Vector2(tX + DST_THRESHOLD, tY);
        } else {
            this.target = new Vector2(tX - DST_THRESHOLD, tY);
        }

        this.facingLeft = facingLeft;
        this.crs.controlPoints = new Vector2[1];
        this.rotateClockwise = MathUtils.randomBoolean();
        this.rotation = (float)MathUtils.random(0, 359);
        this.controlPoints.clear();
        this.rotationRate = MathUtils.random(600.0F, 650.0F) * Settings.scale;
        this.currentSpeed = 1000.0F * Settings.scale;
        this.color = new Color(.5F, 0.5F, 1F, 0.6F);
        this.duration = 0.7F;
        this.scale = 1.0F * Settings.scale;
        this.renderBehind = MathUtils.randomBoolean();
    }

    public void update() {
        this.updateMovement();
    }

    private void updateMovement() {
        Vector2 tmp = new Vector2(this.pos.x - this.target.x, this.pos.y - this.target.y);
        tmp.nor();
        float targetAngle = tmp.angle();
        this.rotationRate += Gdx.graphics.getDeltaTime() * 2000.0F;
        this.scale += Gdx.graphics.getDeltaTime() * 1.0F * Settings.scale;
        if (!this.stopRotating) {
            if (this.rotateClockwise) {
                this.rotation += Gdx.graphics.getDeltaTime() * this.rotationRate;
            } else {
                this.rotation -= Gdx.graphics.getDeltaTime() * this.rotationRate;
                if (this.rotation < 0.0F) {
                    this.rotation += 360.0F;
                }
            }

            this.rotation %= 360.0F;
            if (!this.stopRotating && Math.abs(this.rotation - targetAngle) < Gdx.graphics.getDeltaTime() * this.rotationRate) {
                this.rotation = targetAngle;
                this.stopRotating = true;
            }
        }

        tmp.setAngle(this.rotation);
        tmp.x *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        tmp.y *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        this.pos.sub(tmp);
        if (this.stopRotating) {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 3.0F;
        } else {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 1.5F;
        }

        if (this.currentSpeed > MAX_VELOCITY) {
            this.currentSpeed = MAX_VELOCITY;
        }

        if (this.target.dst(this.pos) < DST_THRESHOLD) {
            for(int i = 0; i < 5; ++i) {
                if (this.facingLeft) {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(this.target.x + DST_THRESHOLD, this.target.y));
                } else {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(this.target.x - DST_THRESHOLD, this.target.y));
                }
            }

            //CardCrawlGame.sound.playAV("BLUNT_HEAVY", MathUtils.random(0.6F, 0.9F), 0.5F);
            //CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, false);
            this.isDone = true;
        }

        if (!this.controlPoints.isEmpty()) {
            if (!((Vector2)this.controlPoints.get(0)).equals(this.pos)) {
                this.controlPoints.add(this.pos.cpy());
            }
        } else {
            this.controlPoints.add(this.pos.cpy());
        }

        if (this.controlPoints.size() > 3) {
            Vector2[] vec2Array = new Vector2[0];
            this.crs.set((Vector2[])this.controlPoints.toArray(vec2Array), false);

            for(int i = 0; i < 60; ++i) {
                this.points[i] = new Vector2();
                this.crs.valueAt(this.points[i], (float)i / 59.0F);
            }
        }

        if (this.controlPoints.size() > 10) {
            this.controlPoints.remove(0);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            sb.setColor(Color.BLACK);
            float scaleCpy = this.scale;

            int i;
            for(i = this.points.length - 1; i > 0; --i) {
                if (this.points[i] != null) {
                    sb.draw(this.img, this.points[i].x - (float)(this.img.packedWidth / 2), this.points[i].y - (float)(this.img.packedHeight / 2), (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, scaleCpy * 1.5F, scaleCpy * 1.5F, this.rotation);
                    scaleCpy *= 0.98F;
                }
            }

            sb.setBlendFunction(770, 1);
            sb.setColor(this.color);
            scaleCpy = this.scale;

            for(i = this.points.length - 1; i > 0; --i) {
                if (this.points[i] != null) {
                    sb.draw(this.img, this.points[i].x - (float)(this.img.packedWidth / 2), this.points[i].y - (float)(this.img.packedHeight / 2), (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, scaleCpy, scaleCpy, this.rotation);
                    scaleCpy *= 0.98F;
                }
            }

            sb.setBlendFunction(770, 771);
        }

    }

    public void dispose() {
    }

    static {
        MAX_VELOCITY = 4000.0F * Settings.scale;
        VELOCITY_RAMP_RATE = 3000.0F * Settings.scale;
        DST_THRESHOLD = 42.0F * Settings.scale;
    }
}
