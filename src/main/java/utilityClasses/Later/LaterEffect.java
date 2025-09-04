package utilityClasses.Later;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/**
 * For out of combat effects or effects involving visual flair that needs to be cleanly seperated outwith the actions of sfx.
 */
public class LaterEffect extends AbstractGameEffect {
    private Runnable action;

    public LaterEffect(Runnable action) {
        this(action, 0.0F);
    }

    public LaterEffect(Runnable action, float lateTime) {
        this.action = action;
        this.duration = lateTime;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0.0F) {
            this.isDone = true;
            this.action.run();
        }
    }

    public void render(SpriteBatch sb) {}
    public void dispose() {}
}