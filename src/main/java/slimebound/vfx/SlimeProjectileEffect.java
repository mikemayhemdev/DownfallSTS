//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class SlimeProjectileEffect extends AbstractGameEffect {
    private static final float DUR = 0.6F;
    private static Texture img;
    private float sX;
    private float sY;
    private float cX;
    private float cY;
    private float dX;
    private float dY;
    private float yOffset;
    private float bounceHeight;
    private boolean playedSfx = false;
    private boolean rain = false;
    private boolean mute = false;
    private float height = 100f;

    private ArrayList<Vector2> previousPos = new ArrayList();

    public SlimeProjectileEffect(float srcX, float srcY, float destX, float destY, float scale, boolean rain, float duration) {
        this(srcX, srcY, destX, destY, scale, rain, duration, false, false);
    }


    public SlimeProjectileEffect(float srcX, float srcY, float destX, float destY, float scale, boolean rain, float duration, boolean mute, boolean bigHeight) {
        if (img == null) {
            img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/slimeballWhite.png");
        }
        ////SlimeboundMod.logger.info("Slime normal projectile firing");

        this.sX = srcX + 40;
        this.sY = srcY - 40;
        this.cX = this.sX;
        this.cY = this.sY;
        this.dX = destX;
        this.dY = destY;
        this.scale = scale;
        this.rotation = 0.0F;
        this.mute = mute;
        this.duration = duration;
        this.color = new Color(1, 1.0F, 1, 0.0F);

        this.rain = rain;

        if (rain) this.height = MathUtils.random(150F, 350F);
        if (bigHeight) this.height = 400F;


        if (this.sY > this.dY) {
            this.bounceHeight = height * Settings.scale;
        } else {
            this.bounceHeight = this.dY - this.sY + height * Settings.scale;
        }

    }

    public void update() {
        if (!this.playedSfx && !this.mute) {
            this.playedSfx = true;

            CardCrawlGame.sound.playA("MONSTER_SLIME_ATTACK", MathUtils.random(-0.5F, -0.3F));


        }

        this.cX = Interpolation.linear.apply(this.dX, this.sX, this.duration / 0.6F);
        this.cY = Interpolation.linear.apply(this.dY, this.sY, this.duration / 0.6F);
        this.previousPos.add(new Vector2(this.cX + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.cY + this.yOffset + MathUtils.random(-30.0F, 30.0F) * Settings.scale));
        if (this.previousPos.size() > 20) {
            this.previousPos.remove(this.previousPos.get(0));
        }

        if (this.dX > this.sX) {
            this.rotation -= Gdx.graphics.getDeltaTime() * 300.0F;
        } else {
            this.rotation += Gdx.graphics.getDeltaTime() * 300.0F;
        }

        if (this.duration > 0.3F) {
            this.color.a = Interpolation.exp5In.apply(1.0F, 0.5F, (this.duration - 0.3F) / 0.3F) * Settings.scale;
            this.yOffset = Interpolation.circleIn.apply(this.bounceHeight, 0.0F, (this.duration - 0.3F) / 0.3F) * Settings.scale;
        } else {
            this.yOffset = Interpolation.circleOut.apply(0.0F, this.bounceHeight, this.duration / 0.3F) * Settings.scale;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {

            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {

        sb.setColor(Color.BLACK);

        sb.draw(img, this.cX - (float) (img.getWidth() / 2), this.cY - (float) (img.getHeight() / 2) + this.yOffset, (float) img.getWidth() / 2.0F, (float) img.getHeight() / 2.0F, (float) img.getWidth(), (float) img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 30, 29, false, false);

        sb.setColor(new Color(1F, 0.5F, 1.0F, 100F));

        sb.draw(img, this.cX - (float) (img.getWidth() / 2), this.cY - (float) (img.getHeight() / 2) + this.yOffset, (float) img.getWidth() / 2.0F, (float) img.getHeight() / 2.0F, (float) img.getWidth(), (float) img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 30, 29, false, false);
        //sb.draw(img, this.cX - (float)(img.getWidth() / 2), this.cY - (float)(img.getHeight() / 2) + this.yOffset, (float)img.getWidth() / 2.0F, (float)img.getHeight() / 2.0F, (float)img.getWidth(), (float)img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 38, 38, false, false);

    }

    public void dispose() {
        img.dispose();
        this.isDone = true;
    }
}
