package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;

public class SpookyFlameBarrier extends AbstractGameEffect {
    private float x;
    private float y;
    private static final float X_RADIUS;
    private static final float Y_RADIUS;
    private boolean flashedBorder = true;
    private Vector2 v = new Vector2(0.0F, 0.0F);

    public SpookyFlameBarrier(float x, float y) {
        this.duration = 0.5F;// 25
        this.x = x;// 26
        this.y = y;// 27
        this.renderBehind = false;// 28
    }// 29

    public void update() {
        if (this.flashedBorder) {// 32
            CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);// 33
            this.flashedBorder = false;// 34
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(new Color(1.0F, 1.0F, 0.1F, 1.0F)));// 35
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(0.1F, 1.0F, 0.1F, 1.0F)));// 36
        }

        float tmp = Interpolation.fade.apply(-209.0F, 30.0F, this.duration / 0.5F) * 0.017453292F;// 39
        this.v.x = MathUtils.cos(tmp) * X_RADIUS;// 40
        this.v.y = -MathUtils.sin(tmp) * Y_RADIUS;// 41
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + this.v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.y + this.v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));// 43 45 46
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + this.v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.y + this.v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));// 47 49 50
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + this.v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.y + this.v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));// 51 53 54
        AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x + this.v.x, this.y + this.v.y));// 56
        this.duration -= Gdx.graphics.getDeltaTime();// 58
        if (this.duration < 0.0F) {// 59
            this.isDone = true;// 60
        }

    }// 62

    public void render(SpriteBatch sb) {
    }// 67

    public void dispose() {
    }// 71

    static {
        X_RADIUS = 200.0F * Settings.scale;// 20
        Y_RADIUS = 250.0F * Settings.scale;
    }
}
