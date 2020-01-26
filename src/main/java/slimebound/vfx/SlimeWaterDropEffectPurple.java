package slimebound.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;


public class SlimeWaterDropEffectPurple extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private float x;
    private float y;
    private static Texture[] imgs = null;
    private int frame = 0;
    private float animTimer = 0.1F;
    private static final int W = 64;

    public SlimeWaterDropEffectPurple(float x, float y) {
        this.x = x;
        this.y = y;

        if (imgs == null) {
            imgs = new Texture[6];
            imgs[0] = ImageMaster.loadImage("images/vfx/water_drop/drop1.png");
            imgs[1] = ImageMaster.loadImage("images/vfx/water_drop/drop2.png");
            imgs[2] = ImageMaster.loadImage("images/vfx/water_drop/drop3.png");
            imgs[3] = ImageMaster.loadImage("images/vfx/water_drop/drop4.png");
            imgs[4] = ImageMaster.loadImage("images/vfx/water_drop/drop5.png");
            imgs[5] = ImageMaster.loadImage("images/vfx/water_drop/drop6.png");
        }

        this.frame = 0;
        this.scale = (MathUtils.random(2.5F, 3.0F) * Settings.scale);
        this.rotation = 0.0F;
        this.scale *= Settings.scale;
        this.color = new Color(0.65F, 0.2F, 0.65F, 1F);
    }
    public void dispose() {
        this.isDone = true;

    }
    public void update() {
        this.color.a = com.megacrit.cardcrawl.helpers.MathHelper.fadeLerpSnap(this.color.a, 1.0F);
        this.animTimer -= Gdx.graphics.getDeltaTime();
        if (this.animTimer < 0.0F) {
            this.animTimer += 0.1F;
            this.frame += 1;

            if (this.frame == 3) {
                for (int i = 0; i < 3; i++) {
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue.add(new SlimeDeathParticleEffect(this.x, this.y, new Color(0.65F, 0.2F, 0.65F, 1F)));
                }
            }

            if (this.frame > 5) {
                this.frame = 5;
                this.isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        switch (this.frame) {
            case 0:
                sb.draw(imgs[0], this.x - 32.0F, this.y - 32.0F + 40.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.rotation, 0, 0, 64, 64, false, false);


                break;
            case 1:
                sb.draw(imgs[1], this.x - 32.0F, this.y - 32.0F + 20.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.rotation, 0, 0, 64, 64, false, false);


                break;
            case 2:
                sb.draw(imgs[2], this.x - 32.0F, this.y - 32.0F + 10.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.rotation, 0, 0, 64, 64, false, false);


                break;
            case 3:
                sb.draw(imgs[3], this.x - 32.0F, this.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.rotation, 0, 0, 64, 64, false, false);


                break;
            case 4:
                sb.draw(imgs[4], this.x - 32.0F, this.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.rotation, 0, 0, 64, 64, false, false);


                break;
            case 5:
                sb.draw(imgs[5], this.x - 32.0F, this.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, this.rotation, 0, 0, 64, 64, false, false);


                break;
        }
    }
}


