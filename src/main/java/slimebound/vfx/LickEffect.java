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

public class LickEffect extends AbstractGameEffect {
    private static final float DUR = 0.6F;
    private static Texture img;
    private float tX;
    private float tY;
    private float dY;
    private float cX;
    private float cY;
    private boolean playedSfx = false;
    private boolean rain = false;
    private float height = 100f;
    private float alpha = 100f;
    private Color tongueColor;

    private ArrayList<Vector2> previousPos = new ArrayList();

    public LickEffect(float tX, float tY, float duration, Color tongueColor) {
        if (img == null) {
            img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/tongue.png");
        }
        ////SlimeboundMod.logger.info("Slime lick effect firing");

        this.tX = tX - 115 * Settings.scale;
        this.tY = tY - 50 * Settings.scale;

        this.cX = this.tX;
        this.cY = this.tY;
        this.dY = this.tY + 110 * Settings.scale;
        this.scale = 1.3F;
        this.rotation = 0.0F;
        this.duration = duration;
        this.startingDuration = duration;
        this.tongueColor = tongueColor;
        this.color = tongueColor;
        this.renderBehind = false;

    }

    public void update() {
        if (!this.playedSfx) {
            this.playedSfx = true;

            CardCrawlGame.sound.playA("MONSTER_SLIME_ATTACK", MathUtils.random(-.3F, -.4F));


        }
        if (this.duration > (this.startingDuration * 0.7F)) {
            this.alpha = Interpolation.linear.apply(0.75F, 0F, (this.duration - (this.startingDuration * 0.7F)) / (this.startingDuration - (this.startingDuration * 0.7F)));
        }
        if (this.duration < (this.startingDuration * 0.3F)) {
            this.alpha = Interpolation.linear.apply(0F, 0.75F, (this.duration) / (this.startingDuration * 0.3F));
        }
        ////SlimeboundMod.logger.info("alpha " + this.alpha);

        this.cY = Interpolation.pow2Out.apply(this.dY, this.tY, this.duration / this.startingDuration);
        ////SlimeboundMod.logger.info("X " + this.cX + " Y " + this.cY);


        this.rotation = Interpolation.pow4Out.apply(100F, 0F, this.duration / this.startingDuration);
        // //SlimeboundMod.logger.info("rotation " + this.rotation);

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {

            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {

        sb.setColor(new Color(0F, 0F, 0F, this.alpha));

        sb.draw(img, this.cX - (float) (img.getWidth() / 2), this.cY - (float) (img.getHeight() / 2), (float) img.getWidth() / 2.0F, (float) img.getHeight() / 2.0F, (float) img.getWidth(), (float) img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 84, 84, false, false);

        sb.setColor(new Color(this.tongueColor.r, this.tongueColor.g, this.tongueColor.b, this.alpha));

        sb.draw(img, this.cX - (float) (img.getWidth() / 2), this.cY - (float) (img.getHeight() / 2), (float) img.getWidth() / 2.0F, (float) img.getHeight() / 2.0F, (float) img.getWidth(), (float) img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 84, 84, false, false);
        //sb.draw(img, this.cX - (float)(img.getWidth() / 2), this.cY - (float)(img.getHeight() / 2) + this.yOffset, (float)img.getWidth() / 2.0F, (float)img.getHeight() / 2.0F, (float)img.getWidth(), (float)img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 38, 38, false, false);

    }

    public void dispose() {
        img.dispose();
        this.isDone = true;
    }
}
