//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package awakenedOne.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;

public class InflameNoSound extends AbstractGameEffect {
    float x;
    float y;

    public InflameNoSound(AbstractCreature creature) {
        this.x = creature.hb.cX;
        this.y = creature.hb.cY;
    }

    public void update() {

        int i;
        for (i = 0; i < 75; ++i) {
            AbstractDungeon.effectsQueue.add(new FlameParticleEffect(this.x, this.y));
        }

        for (i = 0; i < 20; ++i) {
            AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x, this.y));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
