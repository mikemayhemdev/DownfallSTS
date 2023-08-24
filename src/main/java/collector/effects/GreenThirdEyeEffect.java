package collector.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ThirdEyeParticleEffect;

public class GreenThirdEyeEffect extends AbstractGameEffect {
    private float x;
    private float y;

    public GreenThirdEyeEffect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, 800.0F, 0.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, -800.0F, 0.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, 0.0F, 500.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, 0.0F, -500.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, 600.0F, 0.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, -600.0F, 0.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, 0.0F, 400.0F));
        AbstractDungeon.effectsQueue.add(new GreenThirdEyeParticleEffect(this.x, this.y, 0.0F, -400.0F));
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
