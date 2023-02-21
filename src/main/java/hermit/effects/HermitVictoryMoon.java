package hermit.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryEyesEffect;
import hermit.HermitMod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HermitVictoryMoon extends AbstractGameEffect {

    // Settings
    private float duration;

    private float x;
    private float y;

    private static Texture texture = new Texture("hermitResources/images/ending/red_moon.png");
    private static Texture texture_glow = new Texture("hermitResources/images/ending/moon_glow.png");

    public static float alpha = 0;
    private static float wiggle = 0;

    public HermitVictoryMoon() {

        // duration
        this.duration = 1f;
        this.startingDuration = this.duration;

        this.renderBehind = true;

        // Location

        this.y = (float) (Settings.HEIGHT*0.75);
        this.x = (float) (Settings.WIDTH/2);

        alpha = 0;

        // how big
        this.scale = 1f * Settings.scale;
    }

    @Override
    public void render(SpriteBatch sb) {
        this.color = new Color(1, 1, 1, alpha);
        sb.setColor(this.color);
        int w = texture_glow.getWidth();
        int h = texture_glow.getHeight();
        sb.draw(texture_glow, x-w/2f, y-h/2f,
                w/2f, h/2f,
                w, h,
                (float) (1.375f + Math.sin(wiggle)*0.125f), (float) (1.375f + Math.sin(wiggle)*0.125f),
                this.rotation,
                0, 0,
                w, h,
                false, false);
        w = texture.getWidth();
        h = texture.getHeight();
        sb.draw(texture, x-w/2f, y-h/2f,
                w/2f, h/2f,
                w, h,
                this.scale * MathUtils.random(0.999F, 1.001F), this.scale * MathUtils.random(0.999F, 1.001F),
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
        final float dt = Gdx.graphics.getDeltaTime();
        alpha+=dt/2;
        wiggle += dt;
        if (alpha>1f)
            alpha=1f;
    }

    private float clamp(float a, float min, float max) {
        return Math.max(Math.min(a, max), min);
    }
}
