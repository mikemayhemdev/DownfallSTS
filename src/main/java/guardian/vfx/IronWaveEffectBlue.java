//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class IronWaveEffectBlue extends AbstractGameEffect {
    private static final float WAVE_INTERVAL = 0.03F;
    private float waveTimer = 0.0F;
    private float x;
    private float y;
    private float cX;

    public IronWaveEffectBlue(float x, float y, float cX) {
        this.x = x + 120.0F * Settings.scale;
        this.y = y - 20.0F * Settings.scale;
        this.cX = cX;
    }

    public void update() {
        this.waveTimer -= Gdx.graphics.getDeltaTime();
        if (this.waveTimer < 0.0F) {
            this.waveTimer = 0.03F;
            this.x += 160.0F * Settings.scale;
            this.y -= 15.0F * Settings.scale;
            AbstractDungeon.effectsQueue.add(new IronWaveParticleBlue(this.x, this.y));
            if (this.x > this.cX) {
                this.isDone = true;
                CardCrawlGame.sound.playA("ATTACK_DAGGER_6", -0.3F);
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
