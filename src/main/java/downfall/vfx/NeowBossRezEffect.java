package downfall.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class NeowBossRezEffect extends AbstractGameEffect {



    public float cX;
    public float cY;
    public float angle;

    public Color borderColor = Color.CYAN;

    public NeowBossRezEffect(float startX, float startY) {
        this.cX = startX;
        this.cY = startY;

        this.renderBehind = false;


    }

    public void update() {
        AbstractDungeon.effectsQueue.add(new NeowRezFlareParticleEffect(this.cX + MathUtils.random(-100F,100F), this.cY + MathUtils.random(-100F,100F), borderColor, this.angle, this));
        AbstractDungeon.effectsQueue.add(new NeowRezFlareParticleEffect(this.cX + MathUtils.random(-100F,100F), this.cY + MathUtils.random(-100F,100F), borderColor, this.angle, this));
        AbstractDungeon.effectsQueue.add(new NeowRezFlareParticleEffect(this.cX + MathUtils.random(-100F,100F), this.cY + MathUtils.random(-100F,100F), borderColor, this.angle, this));

    }

    public void render(SpriteBatch sb) {
    }

    public void end(){
        this.isDone = true;
    }

    public void dispose() {
    }
}
