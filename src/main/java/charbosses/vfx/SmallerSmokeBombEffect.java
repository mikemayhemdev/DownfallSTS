package charbosses.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SmallerSmokeBombEffect extends AbstractGameEffect {
    private float x;
    private float y;

    public SmallerSmokeBombEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.duration = 0.2F;
    }

    public void update() {
        if (this.duration == 0.2F) {
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");

            for(int i = 0; i < 90; ++i) {
                AbstractDungeon.effectsQueue.add(new ReasonableAmountOfSmoke(this.x, this.y));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            CardCrawlGame.sound.play("APPEAR");
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
