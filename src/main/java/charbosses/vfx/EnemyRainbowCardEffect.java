//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package charbosses.vfx;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EnemyRainbowCardEffect extends AbstractGameEffect {
    float x;
    float y;
    private AtlasRegion img;

    public EnemyRainbowCardEffect() {
        this.img = ImageMaster.CRYSTAL_IMPACT;
        this.x = AbstractCharBoss.boss.hb.cX - (float)this.img.packedWidth / 2.0F;
        this.y = AbstractCharBoss.boss.hb.cY - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 1.5F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale;
        this.color = Color.CYAN.cpy();
        this.color.a = 0.0F;
        this.renderBehind = true;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.sound.playA("HEAL_3", 0.5F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.01F, this.duration - this.startingDuration / 2.0F) * Settings.scale;
        } else {
            this.color.a = Interpolation.fade.apply(0.01F, 1.0F, this.duration / (this.startingDuration / 2.0F)) * Settings.scale;
        }

        this.scale = Interpolation.elasticIn.apply(4.0F, 0.01F, this.duration / this.startingDuration) * Settings.scale;
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0F, 0.2F, 0.2F, this.color.a));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.15F, this.scale * 1.15F, 0.0F);
        sb.setColor(new Color(1.0F, 1.0F, 0.2F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, 0.0F);
        sb.setColor(new Color(0.2F, 1.0F, 0.2F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.85F, this.scale * 0.85F, 0.0F);
        sb.setColor(new Color(0.2F, 0.7F, 1.0F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.7F, this.scale * 0.7F, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
