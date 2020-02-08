package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class SlimeIntentEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = .5F;
    private static final float FLASH_INTERVAL = 0.17F;
    private float intervalTimer = 0.0F;
    private Texture img;
    private AbstractOrb o;

    public SlimeIntentEffect(Texture img, AbstractOrb o, float duration) {
        this.duration = duration;
        this.img = img;
        this.o = o;
    }

    public void update() {
        this.intervalTimer -= Gdx.graphics.getDeltaTime();

        com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue.add(new SlimeIntentParticle(this.img, this.o));


        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {


            this.isDone = true;
        }
    }

    public void dispose() {
        this.img.dispose();
        this.isDone = true;

    }

    public void render(SpriteBatch sb) {
    }
}


