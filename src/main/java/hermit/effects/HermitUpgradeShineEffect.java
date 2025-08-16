package hermit.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;

public class HermitUpgradeShineEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private boolean clang1 = false;
    private boolean clang2 = false;

    public HermitUpgradeShineEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.duration = 0.8F;
    }

    public void update() {
        if (this.duration < 0.6F && !this.clang1) {
            CardCrawlGame.sound.play("CARD_UPGRADE");
            this.clang1 = true;
            this.clank(this.x - 80.0F * Settings.scale, this.y + 0.0F * Settings.scale);
            CardCrawlGame.screenShake.shake(ShakeIntensity.LOW, ShakeDur.SHORT, false);
        }

        if (this.duration < 0.2F && !this.clang2) {
            this.clang2 = true;
            this.clank(this.x + 90.0F * Settings.scale, this.y - 110.0F * Settings.scale);
            CardCrawlGame.screenShake.shake(ShakeIntensity.LOW, ShakeDur.SHORT, false);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.clank(this.x + 30.0F * Settings.scale, this.y + 120.0F * Settings.scale);
            this.isDone = true;
            CardCrawlGame.screenShake.shake(ShakeIntensity.LOW, ShakeDur.SHORT, false);
        }

    }

    private void clank(float x, float y) {
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));
        if (!Settings.DISABLE_EFFECTS) {
            for(int i = 0; i < 30; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
            }

        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
