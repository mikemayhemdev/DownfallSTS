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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EnemyMiracleEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private AtlasRegion img;
    private Color altColor;
    private String sfxUrl = "HEAL_3";

    public EnemyMiracleEffect(Color setColor, Color altColor, String setSfx) {
        this.img = ImageMaster.CRYSTAL_IMPACT;
        this.x = AbstractCharBoss.boss.hb.cX - (float)this.img.packedWidth / 2.0F;
        this.y = AbstractCharBoss.boss.hb.cY - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.7F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale;
        this.altColor = altColor;
        this.color = setColor.cpy();
        this.color.a = 0.0F;
        this.renderBehind = false;
        this.sfxUrl = setSfx;
    }

    public EnemyMiracleEffect() {
        this.img = ImageMaster.CRYSTAL_IMPACT;
        this.x = AbstractCharBoss.boss.hb.cX - (float)this.img.packedWidth / 2.0F;
        this.y = AbstractCharBoss.boss.hb.cY - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.7F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale;
        this.altColor = new Color(1.0F, 0.6F, 0.2F, 0.0F);
        this.color = Settings.GOLD_COLOR.cpy();
        this.color.a = 0.0F;
        this.renderBehind = false;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.sound.playA(this.sfxUrl, 0.5F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.01F, this.duration - this.startingDuration / 2.0F) * Settings.scale;
        } else {
            this.color.a = Interpolation.fade.apply(0.01F, 1.0F, this.duration / (this.startingDuration / 2.0F)) * Settings.scale;
        }

        this.scale = Interpolation.pow5In.apply(2.4F, 0.3F, this.duration / this.startingDuration) * Settings.scale;
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        this.altColor.a = this.color.a;
        sb.setColor(this.altColor);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.1F, this.scale * 1.1F, 0.0F);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.9F, this.scale * 0.9F, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
