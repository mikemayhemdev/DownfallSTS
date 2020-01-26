package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class SlimeIntentParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = 0.75F;
    private static final float START_SCALE = 1.2F * com.megacrit.cardcrawl.core.Settings.scale;
    private float scale = 0.01F;
    private static int W;
    private Texture img;
    private float x;
    private float ox;
    public AbstractOrb o;
    private float y;

    public SlimeIntentParticle(Texture img, AbstractOrb o) {
        this.duration = 0.25F;
        this.img = img;
        W = img.getWidth();
        this.o = o;
        this.ox = o.cX;
        this.x = (o.cX - W / 2.0F);
        this.y = ((o.cY - W / 2.0F) + 65);
        this.renderBehind = true;
    }
    public void dispose() {
        this.img.dispose();
        this.isDone = true;
    }
    public void update() {


        this.scale = Interpolation.pow2Out.apply(START_SCALE, 0.01F, this.duration);


        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {

        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1F, 1F, 1F, this.duration / 2));
        sb.draw(this.img, this.x, this.y, W / 2.0F, W / 2.0F, W, W, this.scale, this.scale, 0.0F, 0, 0, W, W, false, false);
        sb.setBlendFunction(770, 771);


    }
}


