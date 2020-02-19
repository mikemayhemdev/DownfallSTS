package evilWithin.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StealRelicEffect extends AbstractGameEffect {

    private static final int flighttime = 40;
    private static final int dispersalspeed = 100;

    private int frames;
    private AbstractCreature ac;
    private AbstractRelic ar;

    private float startX;
    private float startY;

    private float currentX;
    private float currentY;

    private float opacity;

    public StealRelicEffect(AbstractRelic ar, AbstractCreature ac) {
        this.ar = ar;
        this.ac = ac;
        this.startX = ar.currentX;
        this.startY = ar.currentY;
        this.currentX = ar.currentX;
        this.currentY = ar.currentY;
        this.opacity = 1F;
        this.frames = -flighttime;
    }


    public void render(SpriteBatch sb) {
        sb.setColor(1F, 1F, 1F, opacity);
        sb.draw(ar.img, currentX - 64.0F, currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.rotation, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
    }

    public void update() {
        if (frames < flighttime) {
            this.currentX = this.startX + sigmoid(ac.hb.cX - this.startX, 6F / flighttime, frames);
            this.currentY = this.startY + sigmoid(ac.hb.y + ac.hb.height - this.startY, 6F / flighttime, frames++);
        } else if (opacity > 0) {
            opacity -= 1F / dispersalspeed;
            if (opacity < 0F)
                opacity = 0F;
        } else {
            this.isDone = true;
        }
    }

    public void dispose() {

    }


    private float sigmoid(float endvalue, float steepness, float curval) {
        return endvalue / (1 + (float) Math.pow(Math.E, -steepness * curval));
    }
}