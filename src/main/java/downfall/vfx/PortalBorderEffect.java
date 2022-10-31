package downfall.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PortalBorderEffect extends AbstractGameEffect {
    private float vfxTimer = 0.0F;

    private final float NON_ORBITAL_ADJUSTMENT_MIN_SPEED = 400.0f * Settings.scale;
    private final float NON_ORBITAL_ADJUSTMENT_MAX_SPEED = 600.0f * Settings.scale;
    private final int TARGET_WISP_COUNT_MAX_SIZE_ELLIPSE = 12;
    private final float DEFAULT_ORBIT_DURATION = 8F;

    private float ELLIPSIS_WIDTH = 235F * Settings.scale;
    private float ELLIPSIS_HEIGHT = 235F * Settings.scale;

    private float ELLIPSIS_BASE_WIDTH = 235F * Settings.scale;
    private float ELLIPSIS_BASE_HEIGHT = 235F * Settings.scale;

    public float ELLIPSIS_SCALE = 1F;

    private float ORBIT_DURATION = DEFAULT_ORBIT_DURATION;
    private float NON_ORBITAL_ADJUSTMENT_SPEED = 200.0f * Settings.scale;
    private float ELLIPSIS_X = 0.0f;
    private float ELLIPSIS_Y = 0.0f;
    private boolean orbitClockwise = false;

    public float cX;
    public float cY;
    private float tX;
    private float tY;
    public float initialAngle;
    public float angle;
    public float orbitalInterval;

    public Color borderColor = Color.VIOLET;

    public PortalBorderEffect(float startX, float startY, float angle) {
        this(startX,startY,angle, 1F);
    }

    public PortalBorderEffect(float startX, float startY, float angle, float scale) {
        this.cX = startX;
        this.cY = startY;
        this.tX = startX;
        this.tY = startY;

        ELLIPSIS_X = startX;
        ELLIPSIS_Y = startY;

        this.initialAngle = angle;
        ////SlimeboundMod.logger.info("new border effect, angle: " + initialAngle + " " + orbitalInterval + " " + ORBIT_DURATION);

        this.renderBehind = false;

        this.ELLIPSIS_SCALE *= scale;

        this.calculateEllipseSize();
    }

    public void calculateNewPosition(){
        //calculate the angle given its current orbital duration
        angle = initialAngle + 360.0f * (orbitalInterval / ORBIT_DURATION);
        if (angle > 360.0f) {
            angle -= 360.0f;
        }
        if (orbitClockwise) {
            angle = 360.0f - angle;
        }

        //based on Angle, find the target X coordinate
        float tmp = angle * ((float)Math.PI / 180.0f);
        tX = (ELLIPSIS_WIDTH * ELLIPSIS_HEIGHT) / (float)Math.sqrt((ELLIPSIS_HEIGHT * ELLIPSIS_HEIGHT) + ((ELLIPSIS_WIDTH * ELLIPSIS_WIDTH) * (Math.tan(tmp) * Math.tan(tmp))));
        if (90.0f < angle && angle < 270.0f) {
            tX *= -1;
        }

        //based on the target X coordinate, find the target Y coordinate
        tY = (float)Math.sqrt(((ELLIPSIS_WIDTH * ELLIPSIS_WIDTH * ELLIPSIS_HEIGHT * ELLIPSIS_HEIGHT) - (tX * tX * ELLIPSIS_HEIGHT * ELLIPSIS_HEIGHT)) / (ELLIPSIS_WIDTH * ELLIPSIS_WIDTH));
        if (180.0f < angle && angle < 360.0f) {
            tY *= -1;
        }

        //normalize target coordinates to ellipse position
        tX += ELLIPSIS_X;
        tY += ELLIPSIS_Y;

        //move towards target coordinates

        cX = tX;
        cY = tY;

        //tick duration
        orbitalInterval += Gdx.graphics.getDeltaTime();
        if (orbitalInterval > ORBIT_DURATION) {
            orbitalInterval -= ORBIT_DURATION;
        }
    }

    public void calculateEllipseSize() {
        ELLIPSIS_WIDTH = ELLIPSIS_BASE_WIDTH * ELLIPSIS_SCALE;
        ELLIPSIS_HEIGHT = ELLIPSIS_BASE_HEIGHT * ELLIPSIS_SCALE;

        NON_ORBITAL_ADJUSTMENT_SPEED = Interpolation.linear.apply(NON_ORBITAL_ADJUSTMENT_MIN_SPEED, NON_ORBITAL_ADJUSTMENT_MAX_SPEED, (float) (Math.min(TARGET_WISP_COUNT_MAX_SIZE_ELLIPSE, CustomAnimatedNPC.borderEffectCount)) / (float) TARGET_WISP_COUNT_MAX_SIZE_ELLIPSE);
    }

    public void update() {
        ////SlimeboundMod.logger.info("pre: " + this.cX + ", " + this.cY);
        calculateNewPosition();
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            this.vfxTimer = 0.016F;
            AbstractDungeon.effectsQueue.add(new PortalEdgeFlareParticleEffect(this.cX, this.cY, borderColor, this.angle, this));
            //AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.cX, this.cY, Color.PURPLE));
//AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(this.x, this.y));
        }
        ////SlimeboundMod.logger.info("post: " + this.x + ", " + this.y);

    }

    public void render(SpriteBatch sb) {
    }

    public void end(){
        this.isDone = true;
    }

    public void dispose() {
    }
}
