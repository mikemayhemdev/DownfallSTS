package slimebound.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class SlimeIntentMovementEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = .5F;
    private static final float FLASH_INTERVAL = 0.17F;
    private float intervalTimer = 0.0F;
    private AbstractOrb o;

    public SlimeIntentMovementEffect(AbstractOrb o, float duration) {
        this.duration = duration;
        this.o = o;
    }

    public void dispose() {
        this.isDone = true;

    }

    public void update() {
        this.intervalTimer -= Gdx.graphics.getDeltaTime();

        com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue.add(new SlimeIntentMovement(this.o));


        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {


            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }
}


