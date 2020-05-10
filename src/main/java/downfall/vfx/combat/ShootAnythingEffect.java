package downfall.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShootAnythingEffect extends AbstractGameEffect {

    private static final float gravity = 0.5F * Settings.scale;
    private static final float frictionX = 0.1F * Settings.scale;
    private static final float frictionY = 0.2F * Settings.scale;
    private static final float dispersalspeed = 4;
    public boolean finishedAction;
    private int flightTime;
    private CandyInfo letsago;

    public ShootAnythingEffect(AbstractCreature target, AbstractCreature source, Texture img, int flight) {
        letsago = new CandyInfo(target, source, img);
        flightTime = flight;
    }

    @Override
    public void render(SpriteBatch sb) {
        letsago.render(sb);
        sb.setColor(Color.WHITE);
    }

    @Override
    public void update() {
        boolean finishedEffect = true;

        int wahoo = letsago.update();

        if (wahoo != 3) {
            finishedEffect = false;
        }

        if (wahoo == 1) {
            finishedAction = true;
        }

        if (finishedEffect) {
            this.isDone = true;
        }
    }

    public void dispose() {

    }

    class CandyInfo {
        private float x;
        private float y;
        private float targetX;
        private float targetY;
        private float rotation;
        private float radialvelocity;
        private float bounceplane;
        private float opacity;
        private int hit;
        private int frames;
        private AbstractCreature ac;
        private AbstractCreature source;
        private Texture image;

        CandyInfo(AbstractCreature ac, AbstractCreature source, Texture blah) {
            this.source = source;

            targetX = ac.hb.cX + MathUtils.random(ac.hb.width) - ac.hb.width * 1 / 4;
            targetY = ac.hb.cY + MathUtils.random(ac.hb.height) - ac.hb.height * 1 / 4;

            x = source.hb.cX;
            y = source.hb.cY;

            this.ac = ac;

            hit = 0;
            frames = 0;

            bounceplane = 0;

            opacity = 1F;

            this.image = blah;

            rotation = (MathUtils.random(-30.0F, 30.0F));
            radialvelocity = (MathUtils.random(-10.0F, 10.0F));
        }

        public void render(SpriteBatch sb) {
            sb.setColor(1F, 1F, 1F, opacity);
            sb.draw(this.image, (this.x - (this.image.getWidth() / 2F)), (this.y - (this.image.getHeight() / 2F)), this.image.getWidth() / 2F, this.image.getHeight() / 2F, this.image.getWidth(), this.image.getHeight(), Settings.scale, Settings.scale, this.rotation, 0, 0, this.image.getWidth(), this.image.getHeight(), false, false);
        }

        public int update() {
            if (hit == 0) {
                x = source.hb.cX + (targetX - source.hb.cX) / (float) flightTime * frames;
                y = source.hb.cY + (targetY - source.hb.cY) / (float) flightTime * frames;

                if (frames++ == flightTime) {
                    frames = 0;
                    hit = 1;

                    radialvelocity = MathUtils.random(-30, 30);

                    targetX = (targetX - ac.hb.cX - ac.hb.width / 4) / 4;
                    targetY = (targetY - ac.hb.cY) / 4;
                }
            } else {
                this.targetX += (this.targetX > 0 ? -frictionX : frictionX);

                if (y + this.targetY <= bounceplane) {
                    this.targetY = Math.abs(this.targetY);
                    if (this.targetY > 1 * Settings.scale) {
                        this.radialvelocity = MathUtils.random(-30, 30);
                    } else {
                        this.radialvelocity = 0;
                    }
                    hit = 2;
                } else {
                    this.targetY -= (this.targetY > 0 ? frictionY : -frictionY);
                    this.targetY -= gravity;
                }
                x += targetX;
                y += targetY;
                rotation += radialvelocity;

                if (hit > 1) {
                    opacity -= dispersalspeed / 300F;
                    if (opacity <= 0F) {
                        opacity = 0F;
                        hit = 3;
                    }
                }
            }
            return hit;
        }
    }
}