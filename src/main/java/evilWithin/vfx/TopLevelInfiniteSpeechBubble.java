package evilWithin.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.DialogWord.AppearEffect;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;

public class TopLevelInfiniteSpeechBubble extends AbstractGameEffect {
    private static final int RAW_W = 512;
    private float shadow_offset = 0.0F;
    private static final float SHADOW_OFFSET;
    private float x;
    private float y;
    private float scale_x;
    private float scale_y;
    private float wavy_y;
    private float wavyHelper;
    private static final float WAVY_DISTANCE;
    private static final float SCALE_TIME = 0.3F;
    private float scaleTimer = 0.3F;
    private static final float ADJUST_X;
    private static final float ADJUST_Y;
    private boolean facingRight;
    private static final float FADE_TIME = 0.3F;
    private SpeechTextEffect textEffect;

    public TopLevelInfiniteSpeechBubble(float x, float y, String msg) {
        this.textEffect = new SpeechTextEffect(x - 170.0F * Settings.scale, y + 124.0F * Settings.scale, 3.4028235E38F, msg, AppearEffect.BUMP_IN);
        AbstractDungeon.topLevelEffectsQueue.add(this.textEffect);
        this.x = x - ADJUST_X;
        this.y = y + ADJUST_Y;
        this.scaleTimer = 0.3F;
        this.color = new Color(0.8F, 0.9F, 0.9F, 0.0F);
        this.duration = 3.4028235E38F;
        this.facingRight = true;
    }

    public void dismiss() {
        this.duration = 0.3F;
        this.textEffect.duration = 0.3F;
    }

    public void update() {
        this.updateScale();
        this.wavyHelper += Gdx.graphics.getDeltaTime() * 4.0F;
        this.wavy_y = MathUtils.sin(this.wavyHelper) * WAVY_DISTANCE;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration > 0.3F) {
            this.color.a = MathUtils.lerp(this.color.a, 1.0F, Gdx.graphics.getDeltaTime() * 12.0F);
        } else {
            this.color.a = MathUtils.lerp(this.color.a, 0.0F, Gdx.graphics.getDeltaTime() * 12.0F);
        }

        this.shadow_offset = MathUtils.lerp(this.shadow_offset, SHADOW_OFFSET, Gdx.graphics.getDeltaTime() * 4.0F);
    }

    private void updateScale() {
        this.scaleTimer -= Gdx.graphics.getDeltaTime();
        if (this.scaleTimer < 0.0F) {
            this.scaleTimer = 0.0F;
        }

        this.scale_x = Interpolation.circleIn.apply(Settings.scale, Settings.scale * 0.5F, this.scaleTimer / 0.3F);
        this.scale_y = Interpolation.swingIn.apply(Settings.scale, Settings.scale * 0.8F, this.scaleTimer / 0.3F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, this.color.a / 4.0F));
        sb.draw(ImageMaster.SPEECH_BUBBLE_IMG, this.x - 256.0F + this.shadow_offset, this.y - 256.0F + this.wavy_y - this.shadow_offset, 256.0F, 256.0F, 512.0F, 512.0F, this.scale_x, this.scale_y, this.rotation, 0, 0, 512, 512, this.facingRight, false);
        sb.setColor(this.color);
        sb.draw(ImageMaster.SPEECH_BUBBLE_IMG, this.x - 256.0F, this.y - 256.0F + this.wavy_y, 256.0F, 256.0F, 512.0F, 512.0F, this.scale_x, this.scale_y, this.rotation, 0, 0, 512, 512, this.facingRight, false);
    }

    public void dispose() {
    }

    static {
        SHADOW_OFFSET = 16.0F * Settings.scale;
        WAVY_DISTANCE = 2.0F * Settings.scale;
        ADJUST_X = 170.0F * Settings.scale;
        ADJUST_Y = 116.0F * Settings.scale;
    }
}
