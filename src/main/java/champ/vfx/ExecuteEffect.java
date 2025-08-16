package champ.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ExecuteEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private final float timer = 0.1F;
    private final float timerFlip = 0F;
    private final AbstractCreature owner;
    private final boolean useJump;

    public ExecuteEffect(AbstractCreature c, boolean jump) {
        this.duration = .65F;
        owner = c;
        useJump = jump;
    }

    public void update() {
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}

