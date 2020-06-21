//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;
import downfall.downfallMod;

public class TouchPickupSouls extends AbstractGameEffect {
    private static final float RAW_IMG_WIDTH = 32.0F;
    private static final float IMG_WIDTH;
    private Texture img;
    private boolean isPickupable;
    public boolean pickedup;
    private float x;
    private float y;
    private float landingY;
    private boolean flip;
    private float vY;
    private float vX;
    private Hitbox hitbox;

    public TouchPickupSouls() {
        this.isPickupable = false;
        this.pickedup = false;
        this.img = ImageMaster.loadImage(downfallMod.assetPath("images/ui/Souls.png"));

        this.y = MathUtils.random(0F, Settings.HEIGHT * -0.15F);
        this.x = MathUtils.random((float)Settings.WIDTH * 0.05F, (float)Settings.WIDTH * 0.95F) - (float)this.img.getWidth() / 2.0F;
        this.landingY = MathUtils.random(AbstractDungeon.floorY + (float)Settings.HEIGHT * 0.25F, AbstractDungeon.floorY + (float)Settings.HEIGHT * 0.38F);
        this.rotation = 180F + MathUtils.random(-30F, 30F);
        this.flip = MathUtils.randomBoolean();
        this.scale = Settings.scale;

        this.vY = MathUtils.random(-1.0f, 1.0f) + (this.y - this.landingY) / 40.0f;
        this.vX = MathUtils.random(-0.5f, 0.5f);

        this.color = Color.WHITE.cpy();
        this.color.a = 0.8f;
    }

    public TouchPickupSouls(boolean centerOnPlayer) {
        this();
        if (centerOnPlayer) {
            this.x = MathUtils.random(AbstractDungeon.player.drawX - AbstractDungeon.player.hb_w, AbstractDungeon.player.drawX + AbstractDungeon.player.hb_w);

            this.vY *= 0.5f;
        }
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime() * 60.0F;
        this.y -= this.vY * Gdx.graphics.getDeltaTime() * 60.0F;

        if (this.vY < -25f)
        {
            this.vY *= Math.max(0.5f, 1 - (2.5f * Gdx.graphics.getDeltaTime()));
        }
        else if (this.vY < -10f)
        {
            this.vY *= Math.max(0.5f, 1 - (Gdx.graphics.getDeltaTime()));
        }


        if (!this.isPickupable) {
            if (this.y > this.landingY) {
                this.isPickupable = true;
                this.hitbox = new Hitbox(this.x - IMG_WIDTH * 2.0F, this.y - IMG_WIDTH * 2.0F, IMG_WIDTH * 4.0F, IMG_WIDTH * 4.0F);
            }
        } else if (!this.pickedup) {
            this.color.a -= Gdx.graphics.getDeltaTime() * 2.0f;
            this.pickedup = true;
            this.playGainGoldSFX();
            AbstractDungeon.effectsQueue.add(new ShineLinesEffect(this.x, this.y));
        }
        else
        {
            this.color.a -= Gdx.graphics.getDeltaTime() * 2.0f;

            if (this.color.a <= 0)
                this.isDone = true;
        }
    }

    private void playGainGoldSFX() {
        int roll = MathUtils.random(2);
        switch(roll) {
            case 0:
                CardCrawlGame.sound.playA("HEAL_1", -0.3F);
                break;
            case 1:
                CardCrawlGame.sound.playA("HEAL_2", -0.3F);
                break;
            default:
                CardCrawlGame.sound.playA("HEAL_3", -0.3F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F, this.img.getWidth(), this.img.getHeight(), Settings.scale, Settings.scale, this.rotation, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flip, false);
        if (this.hitbox != null) {
            this.hitbox.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        IMG_WIDTH = 32.0F * Settings.scale;
    }
}
