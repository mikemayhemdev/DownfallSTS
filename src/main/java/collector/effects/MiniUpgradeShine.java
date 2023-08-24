package collector.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MiniUpgradeShine extends AbstractGameEffect {
    private final float x;
    private final float y;
    private boolean clang1 = false;
    private boolean clang2 = false;

    public MiniUpgradeShine(float x, float y) {
        this.x = x;
        this.y = y;
        this.duration = 0.8F;
    }

    public void update() {
        if (this.duration < 0.6F && !this.clang1) {
            CardCrawlGame.sound.play("CARD_UPGRADE");
            this.clang1 = true;
            this.clank(this.x - 40.0F * Settings.scale, this.y + 0.0F * Settings.scale);
            //CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.SHORT, false);
        }

        if (this.duration < 0.2F && !this.clang2) {
            this.clang2 = true;
            this.clank(this.x + 45.0F * Settings.scale, this.y - 55.0F * Settings.scale);
            //CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.SHORT, false);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.clank(this.x + 15.0F * Settings.scale, this.y + 60.0F * Settings.scale);
            this.isDone = true;
            //CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.SHORT, false);
        }

    }

    private void clank(float x, float y) {
        AbstractDungeon.topLevelEffectsQueue.add(new MiniHammerImprintEffect(x, y));
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
