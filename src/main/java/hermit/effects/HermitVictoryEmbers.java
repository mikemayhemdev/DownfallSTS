package hermit.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.scene.IroncladVictoryFlameEffect;

public class HermitVictoryEmbers extends AbstractGameEffect {

    // Settings
    final private float gravity = 30.0f;  // Gravity (negative is down)
    private float duration;

    private float x;
    private float y;

    private static Texture texture_normal = new Texture("hermitResources/images/ending/orb_long.png");
    private static Texture texture2_normal = new Texture("hermitResources/images/ending/orb_glow_long.png");

    private static Texture texture_alt = new Texture("hermitResources/images/ending/orb.png");
    private static Texture texture2_alt = new Texture("hermitResources/images/ending/orb_glow.png");

    private Texture texture = texture_normal;
    private Texture texture2 = texture2_normal;

    private float speed = -6400.0f;

    private float direction;

    private float wiggle = 0;
    private boolean random_ember;
    private float alpha = 1;

    public HermitVictoryEmbers() {

        // duration
        this.duration = 5f;
        this.startingDuration = this.duration;

        this.renderBehind = false;
        this.random_ember = MathUtils.randomBoolean();

        if (this.random_ember)
        {
            speed = -1600;

            texture = texture_alt;
            texture2 = texture2_alt;
        }

        // Location

        float placedir = (float) MathUtils.random(0, 360);

        float h = MathUtils.random(0.7F, 0.85F);
        float dist = h * (float)Settings.WIDTH;
        this.y = (float) (Settings.HEIGHT*0.75 + MathUtils.sinDeg(placedir) * dist);
        this.x = (float) (Settings.WIDTH/2 + MathUtils.cosDeg(placedir) * dist);


        this.direction = placedir;


        this.color = new Color(1, 1, 1, 1F);

        // how big
        this.scale = MathUtils.random(1.0f,2.0f) * Settings.scale;
    }

    @Override
    public void render(SpriteBatch sb) {
        this.color.a = this.alpha * HermitVictoryMoon.alpha;
        sb.setColor(this.color);
        final int w = texture.getWidth();
        final int h = texture.getHeight();
        final int w2 = texture2.getWidth();
        final int h2 = texture2.getHeight();
        sb.draw(texture2, x-w2/2f, y-h2/2f,
                w/2f, h/2f,
                w2, h2,
                (float) (this.scale*(1+0.25*Math.sin(wiggle))), (float) (this.scale*(1+0.25*Math.sin(wiggle))),
                this.rotation,
                0, 0,
                w2, h2,
                false, false);
        sb.draw(texture, x-w/2f, y-h/2f,
                w/2f, h/2f,
                w, h,
                scale, scale,
                this.rotation,
                0, 0,
                w, h,
                false, false);

    }

    @Override
    public void dispose() {
        // throw away the potato
    }

    @Override
    public void update() {
        // Timing
        final float dt = Gdx.graphics.getDeltaTime();
        this.duration -= dt;

        this.rotation = (this.direction);

        this.y += MathUtils.sinDeg(direction) * speed * dt;
        this.x += MathUtils.cosDeg(direction) * speed * dt;

        if (this.random_ember)
        {
            this.speed *= Math.pow(0.975, dt * 60);
            this.scale *= Math.pow(0.975, dt * 60);
            this.alpha *= Math.pow(0.98, dt * 60);
        }
        else {
            this.speed *= Math.pow(0.90, dt * 60);
            this.scale *= Math.pow(0.90, dt * 60);
            this.alpha *= Math.pow(0.93, dt * 60);
        }

        wiggle+=dt*20;

        if (color.a < 0.0125)
            isDone=true;
    }

    private float clamp(float a, float min, float max) {
        return Math.max(Math.min(a, max), min);
    }
}
