package hermit.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class ShortScreenFire extends AbstractGameEffect {
    private float timer = 0.0F;
    private static final float INTERVAL = 0.05F;

    public ShortScreenFire() {
        this.duration = 0.75F;
        this.startingDuration = this.duration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.sound.playV("GHOST_FLAMES",1.0f);
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GREEN));
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            AbstractDungeon.effectsQueue.add(new GreenFireEffect());
            this.timer = 0.05F;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
