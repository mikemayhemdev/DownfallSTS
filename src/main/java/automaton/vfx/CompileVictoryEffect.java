package automaton.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import downfall.util.TextureLoader;
import java.util.ArrayList;

public class CompileVictoryEffect extends AbstractGameEffect {
    private static final float FADE_IN_TIME = 1.0f;
    private static final float TIME_BETWEEN_SEQUENCES = 1.1f;
    private float timer = 0f;
    private float newSequenceTimer = 0f;
    private float overallOpacity;
    public static Color overallColor = Color.WHITE.cpy();
    public static ArrayList<AbstractGameEffect> effects = new ArrayList<>();

    public void update() {
        timer += Gdx.graphics.getDeltaTime();
        overallOpacity = Math.min(timer / FADE_IN_TIME, 1f);
        overallColor = new Color(1f, 1f, 1f, overallOpacity);
        newSequenceTimer -= Gdx.graphics.getDeltaTime();
        if (newSequenceTimer <= 0f) {
            newSequenceTimer += TIME_BETWEEN_SEQUENCES;
            effects.add(new CompileSequence());
        }
    }

    public void render(SpriteBatch sb) {}
    public void dispose() {}

    private static class CompileSequence extends AbstractGameEffect {
        private static final TextureRegion IMAGE = new TextureRegion(TextureLoader.getTexture("bronzeResources/images/vfx/sequence.png"));
        private static final float W = IMAGE.getTexture().getWidth();
        private static final float H = IMAGE.getTexture().getHeight();
        private static final float EXPAND_TIME = 0.2f;
        private static final float COMBINE_DELAY = 1.5f;
        private static final float COMBINE_TIME = 0.65f;
        private static final float STRETCH_WIDTH = 110f / 300f;
        public static final float DURATION = COMBINE_DELAY + COMBINE_TIME;
        private float x, y;
        private ArrayList<CardToCompile> cards = new ArrayList<>();
        private float imgScale = 0f;
        private float xStretch = 1f;
        private float timer = 0f;

        public CompileSequence() {
            x = Settings.WIDTH * MathUtils.random(0.1f, 0.9f);
            y = Settings.HEIGHT * MathUtils.random(0.2f, 0.9f);
            rotation = MathUtils.random(-30f, 30f);
            scale *= MathUtils.random(0.8f, 1.2f);
            cards.add(new CardToCompile(-95f, 0.2f, this));
            cards.add(new CardToCompile(95f, 0.35f, this));
            cards.add(new CardToCompile(0f, 0.275f, this));
        }

        public void update() {
            effects.addAll(cards);
            cards.clear();
            timer += Gdx.graphics.getDeltaTime();
            if (timer < EXPAND_TIME)
                imgScale = 1f - (float)Math.pow(1 - (timer / EXPAND_TIME), 2); //quad ease out
            else
                imgScale = 1f;
            if (timer > COMBINE_DELAY)
                xStretch = STRETCH_WIDTH + (1f - STRETCH_WIDTH) * (1f - (timer - COMBINE_DELAY) / COMBINE_TIME);
            if (timer > DURATION) {
                isDone = true;
                effects.add(new FunctionCard(this));
                effects.add(new ScaledHammerImprintEffect(x, y, scale * 0.5f));
            }
        }

        public void render(SpriteBatch sb) {
            sb.setColor(overallColor);
            sb.draw(IMAGE, x - W/2f, y - H/2f, W/2f, H/2f, W, H, scale * imgScale * xStretch, scale * imgScale, rotation);
        }

        public void dispose() {}

        private static class CardToCompile extends AbstractGameEffect {
            private static final TextureRegion IMAGE = new TextureRegion(TextureLoader.getTexture("bronzeResources/images/vfx/encodable.png"));
            private static final float W = IMAGE.getTexture().getWidth();
            private static final float H = IMAGE.getTexture().getHeight();
            private static final float SLIDE_OFFSET = -100f;
            private static final float SLIDE_TIME = 0.85f;
            private float x, y, tX, tY, sX, sY, eX, eY, delay, imgScale;
            private float timer = 0f;
            private float opacity = 0f;

            public CardToCompile(float offset, float delay, CompileSequence sequence) {
                rotation = sequence.rotation;
                scale = sequence.scale;
                this.delay = delay;
                //target pos
                tX = sequence.x + offset * scale * MathUtils.cosDeg(rotation);
                tY = sequence.y + offset * scale * MathUtils.sinDeg(rotation);
                //start pos
                sX = tX + SLIDE_OFFSET * scale * MathUtils.cosDeg(rotation+90f);
                sY = tY + SLIDE_OFFSET * scale * MathUtils.sinDeg(rotation+90f);
                //end pos
                eX = sequence.x;
                eY = sequence.y;
            }

            public void update() {
                timer += Gdx.graphics.getDeltaTime();
                if (timer > delay && timer < COMBINE_DELAY) {
                    float progress = Math.min((timer - delay) / SLIDE_TIME, 1f);
                    float easedProgress = 1f - (float)Math.pow(1f - progress, 3); //cubic ease out
                    opacity = progress;
                    x = sX + (tX - sX) * easedProgress;
                    y = sY + (tY - sY) * easedProgress;
                    imgScale = easedProgress * 0.6f + (1 - 0.6f);
                } else if (timer > COMBINE_DELAY) {
                    float progress = (timer - COMBINE_DELAY) / COMBINE_TIME;
                    x = tX + (eX - tX) * progress;
                    y = tY + (eY - tY) * progress;
                }
                if (timer > DURATION)
                    isDone = true;
            }

            public void render(SpriteBatch sb) {
                sb.setColor(new Color(1f, 1f, 1f, Math.min(overallColor.a * opacity, 0.7f)));
                sb.draw(IMAGE, x - W/2f, y - H/2f, W/2f, H/2f, W, H, scale * imgScale, scale * imgScale, rotation);
            }

            public void dispose() {}
        }

        private static class FunctionCard extends AbstractGameEffect {
            private static final TextureRegion IMAGE = new TextureRegion(TextureLoader.getTexture("bronzeResources/images/vfx/function.png"));
            private static final float W = IMAGE.getTexture().getWidth();
            private static final float H = IMAGE.getTexture().getHeight();
            private static final float ACCELERATION = -750f;
            private static final float INITIAL_VEL = 600f;
            private float x, y, vX, vY;

            public FunctionCard(CompileSequence sequence) {
                x = sequence.x;
                y = sequence.y;
                scale = sequence.scale;
                rotation = sequence.rotation;
                vX = INITIAL_VEL * scale * MathUtils.cosDeg(rotation+90f);
                vY = INITIAL_VEL * scale * MathUtils.sinDeg(rotation+90f);
            }

            public void update() {
                x += vX * Gdx.graphics.getDeltaTime();
                y += vY * Gdx.graphics.getDeltaTime();
                vY += ACCELERATION * scale * Gdx.graphics.getDeltaTime();
                if (y < -500f) isDone = true;
            }

            public void render(SpriteBatch sb) {
                sb.setColor(overallColor);
                sb.draw(IMAGE, x - W/2f, y - H/2f, W/2f, H/2f, W, H, scale, scale, rotation);
            }

            public void dispose() {}
        }
    }
}