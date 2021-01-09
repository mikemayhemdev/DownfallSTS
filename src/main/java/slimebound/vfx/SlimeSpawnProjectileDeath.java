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
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class SlimeSpawnProjectileDeath extends AbstractGameEffect {
    private static final float DUR = 0.25F;
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
    private boolean skip = false;
    private float height = 100f;
    private Color projectileColor;
    private Color projectileColorDeath;
    private AbstractOrb o;
    private boolean mute;
    private AbstractPlayer p;


    private ArrayList<Vector2> previousPos = new ArrayList();

    public SlimeSpawnProjectileDeath(float srcX, float srcY, AbstractOrb o, float scale, Color color, boolean mute) {
        this(srcX, srcY, o, null, scale, color, mute);
    }


    public SlimeSpawnProjectileDeath(float srcX, float srcY, AbstractOrb o, AbstractPlayer p, float scale, Color color, boolean mute) {
        if (img == null) {
            img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/slimeballWhite.png");
        }
        if (o == null && p == null) {
            this.duration = 0F;
            this.startingDuration = 0F;
            this.skip = true;
        } else {
            this.mute = mute;


            ////SlimeboundMod.logger.info("Slime spawn projectile firing");

            this.sX = srcX;
            // if (!this.mute) CardCrawlGame.sound.playA("SLIME_SPLIT", 0.3f);

            this.sY = srcY;
            this.cX = this.sX;
            this.cY = this.sY;
            if (o == null) {
                this.p = p;

            } else {
                this.o = o;
            }
            this.scale = scale;
            this.rotation = 0.0F;
            this.duration = .35F;
            this.startingDuration = .35F;
            this.projectileColor = new Color(color.r, color.g, color.b, 100F);
        }


    }

    public void update() {
        if (!this.skip) {
            if (o == null) {
                this.cX = Interpolation.linear.apply(this.p.hb.cX, this.sX, this.duration / this.startingDuration);
                this.cY = Interpolation.linear.apply(this.p.hb.cY + 8, this.sY, this.duration / this.startingDuration);


                if (this.p.hb.cX > this.sX) {
                    this.rotation -= Gdx.graphics.getDeltaTime() * 300.0F;
                } else {
                    this.rotation += Gdx.graphics.getDeltaTime() * 300.0F;
                }
            } else {
                this.cX = Interpolation.linear.apply(this.o.cX, this.sX, this.duration / this.startingDuration);
                this.cY = Interpolation.linear.apply(this.o.cY + 8, this.sY, this.duration / this.startingDuration);


                if (this.o.cX > this.sX) {
                    this.rotation -= Gdx.graphics.getDeltaTime() * 300.0F;
                } else {
                    this.rotation += Gdx.graphics.getDeltaTime() * 300.0F;
                }
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {

            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        if (this.cX > Settings.WIDTH * 0.15F) {
            ////SlimeboundMod.logger.info("Projectile C's: " + this.cX + " " + Settings.WIDTH * 0.25F);
            sb.setColor(Color.BLACK);
            sb.draw(img, this.cX - (float) (img.getWidth() / 2), this.cY - (float) (img.getHeight() / 2) + this.yOffset, (float) img.getWidth() / 2.0F, (float) img.getHeight() / 2.0F, (float) img.getWidth(), (float) img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 30, 29, false, false);
            sb.setColor(this.projectileColor);
            ////SlimeboundMod.logger.info("Projectile color: " + this.projectileColor);
            sb.draw(img, this.cX - (float) (img.getWidth() / 2), this.cY - (float) (img.getHeight() / 2) + this.yOffset, (float) img.getWidth() / 2.0F, (float) img.getHeight() / 2.0F, (float) img.getWidth(), (float) img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 30, 29, false, false);
            //sb.draw(img, this.cX - (float)(img.getWidth() / 2), this.cY - (float)(img.getHeight() / 2) + this.yOffset, (float)img.getWidth() / 2.0F, (float)img.getHeight() / 2.0F, (float)img.getWidth(), (float)img.getHeight(), this.scale, this.scale, this.rotation, 0, 0, 38, 38, false, false);

        }
    }

    public void dispose() {
        img.dispose();
        this.isDone = true;
    }
}
