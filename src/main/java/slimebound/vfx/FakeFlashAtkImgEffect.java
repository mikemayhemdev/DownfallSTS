//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FakeFlashAtkImgEffect extends AbstractGameEffect {
    private static final float DURATION = 0.6F;
    private static int blockSound = 0;
    public AtlasRegion img;
    private float x;
    private float y;
    private float sY;
    private float tY;
    private boolean mute;

    public FakeFlashAtkImgEffect(float x, float y, Color color, float scale, boolean mute, float duration) {
        this.duration = duration;
        this.startingDuration = this.duration;
        this.img = this.loadImage();
        this.mute = mute;
        if (this.img != null) {
            this.x = x - (float) this.img.packedWidth / 2.0F;
            y -= (float) this.img.packedHeight / 2.0F;
        }

        this.color = color;
        this.scale = scale * Settings.scale;
        if (!mute) {
            CardCrawlGame.sound.play("ATTACK_POISON", -0.2f);
        }

        this.y = y;

        this.y = y;
        this.sY = y;
        this.tY = y;


    }


    private AtlasRegion loadImage() {

        return ImageMaster.ATK_POISON;

    }


    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.color);
            sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        }

    }

    public void dispose() {
        this.isDone = true;
    }
}
