package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ActiveFireEffect extends AbstractGameEffect {
    private static final float DUR = 1.0F;
    private AtlasRegion img = this.getImg();
    private float x;
    private float y;
    private float vX;
    private float vY;

    public ActiveFireEffect(float x, float y, Color color) {
        this.x = x + MathUtils.random(-2.0F, 2.0F) * Settings.scale - (float) this.img.packedWidth / 2.0F;// 19
        this.y = y + MathUtils.random(-2.0F, 2.0F) * Settings.scale - (float) this.img.packedHeight / 2.0F;// 20
        this.vX = MathUtils.random(-2.0F, 2.0F) * Settings.scale;// 21
        this.vY = MathUtils.random(0.0F, 80.0F) * Settings.scale;// 22
        this.duration = 1.0F;// 23
        this.color = color.cpy();// 24
        this.color.a = 0.0F;// 25
        this.scale = Settings.scale * MathUtils.random(2.0F, 3.0F);// 26
    }// 27

    private AtlasRegion getImg() {
        switch (MathUtils.random(0, 2)) {// 30
            case 0:
                return ImageMaster.TORCH_FIRE_1;// 32
            case 1:
                return ImageMaster.TORCH_FIRE_2;// 34
            default:
                return ImageMaster.TORCH_FIRE_3;// 36
        }
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();// 42
        this.y += this.vY * Gdx.graphics.getDeltaTime();// 43
        this.duration -= Gdx.graphics.getDeltaTime();// 44
        if (this.duration < 0.0F) {// 45
            this.isDone = true;// 46
        }

        this.color.a = this.duration / 2.0F;// 48
    }// 49

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 53
        sb.setColor(this.color);// 54
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);// 55
        sb.setBlendFunction(770, 771);// 66
    }// 67

    public void dispose() {
    }// 72
}
