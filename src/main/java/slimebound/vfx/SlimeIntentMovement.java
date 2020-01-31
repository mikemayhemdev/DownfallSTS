package slimebound.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class SlimeIntentMovement extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {

    public AbstractOrb o;
    private float ox;


    public SlimeIntentMovement(AbstractOrb o) {
        this.duration = 0.5F;
        this.o = o;
        this.ox = o.cX;

        this.renderBehind = true;
    }

    public void dispose() {
        this.isDone = true;

    }

    public void update() {


        o.cX = Interpolation.pow2.apply(ox, ox + 20, this.duration);


        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setBlendFunction(770, 771);

    }
}


