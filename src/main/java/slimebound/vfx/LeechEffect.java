//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class LeechEffect extends AbstractGameEffect {
    int count = 0;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float timer = 0.0F;
    private Color color;

    public LeechEffect(float sX, float sY, float tX, float tY, int count, Color color) {
        this.sX = sX;//* Settings.scale;
        this.sY = sY;//* Settings.scale;
        this.tX = tX;
        this.tY = tY;
        this.count = count;
        this.color = color;
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += MathUtils.random(0.05F, 0.15F);
            AbstractDungeon.effectsQueue.add(new LeechEffectParticle(this.sX, this.sY, this.tX, this.tY, color));
            --this.count;
            if (this.count == 0) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
        this.isDone = true;
    }
}
