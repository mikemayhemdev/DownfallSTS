//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SelfSpikesEffect extends AbstractGameEffect {
    private boolean mute;
    private float scale;
    private int spikeCount;

    public SelfSpikesEffect(Color clr, int count, boolean noAudio, float size) {
        mute = noAudio;
        scale = size;
        this.color = clr.cpy();
        spikeCount = count;
    }

    public void update() {
        this.isDone = true;
        int i;
            for(i = spikeCount; i > 0; --i) {
                AbstractDungeon.effectsQueue.add(new DaggerThrowAnyColorEffect(
                        AbstractDungeon.player.hb.cX + 350F * Settings.scale,
                        AbstractDungeon.player.hb.cY,
                        color,
                        (360F/(spikeCount * 2)) * i, false, true, mute, scale));
            }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
