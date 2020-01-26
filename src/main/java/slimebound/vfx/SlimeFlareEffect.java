package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class SlimeFlareEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static TextureAtlas.AtlasRegion outer;
    private static TextureAtlas.AtlasRegion inner;
    private float scaleY;
    private static final float DUR = 0.5F;
    private AbstractOrb orb;
    private OrbFlareColor flareColor;
    private Color color2;

    public static enum OrbFlareColor {
        POISON, AGGRESSIVE, SLIMING, LICKING, HEX, CULTIST, TORCHHEAD, BRONZE;

        private OrbFlareColor() {
        }
    }
    public void dispose() {
        this.isDone = true;

    }
    public SlimeFlareEffect(AbstractOrb orb, OrbFlareColor setColor) {
        if (outer == null) {
            outer = ImageMaster.vfxAtlas.findRegion("combat/orbFlareOuter");
            inner = ImageMaster.vfxAtlas.findRegion("combat/orbFlareInner");
        }

        this.orb = orb;
        this.renderBehind = true;
        this.duration = 0.5F;
        this.startingDuration = 0.5F;
        this.flareColor = setColor;
        setColor();
        this.scale = (2.0F * Settings.scale);
        this.scaleY = 0.0F;
    }

    private void setColor() {
        switch (this.flareColor) {
            case POISON:
                this.color = Color.FOREST.cpy();
                this.color2 = Color.LIGHT_GRAY.cpy();
                break;
            case AGGRESSIVE:
                this.color = Color.DARK_GRAY.cpy();
                this.color2 = Color.LIGHT_GRAY.cpy();
                break;
            case LICKING:
                this.color = Color.CYAN.cpy();
                this.color2 = Color.WHITE.cpy();
                break;
            case SLIMING:
                this.color = Color.MAROON.cpy();
                this.color2 = Color.CYAN.cpy();
                break;
            case HEX:
                this.color = Color.CORAL.cpy();
                this.color2 = Color.CYAN.cpy();
                break;
            case TORCHHEAD:
                this.color = Color.GOLDENROD.cpy();
                this.color2 = Color.GREEN.cpy();
                break;
            case CULTIST:
                this.color = Color.VIOLET.cpy();
                this.color2 = Color.BROWN.cpy();
                break;
            case BRONZE:
                this.color = Color.GOLDENROD.cpy();
                this.color2 = Color.GOLD.cpy();
                break;
        }

    }


    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.duration = 0.0F;
            this.isDone = true;
        }

        this.scaleY = Interpolation.elasticIn.apply(2.2F, 0.8F, this.duration * 2.0F);
        this.scale = Interpolation.elasticIn.apply(2.1F, 1.9F, this.duration * 2.0F);
        this.color.a = Interpolation.pow2Out.apply(0.0F, 0.6F, this.duration * 2.0F);
        this.color2.a = this.color.a;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color2);
        sb.draw(inner, this.orb.cX - inner.packedWidth / 2.0F, this.orb.cY - inner.packedHeight / 2.0F, inner.packedWidth / 2.0F, inner.packedHeight / 2.0F, inner.packedWidth, inner.packedHeight, this.scale * Settings.scale * 1.1F, this.scaleY * Settings.scale,


                MathUtils.random(-1.0F, 1.0F));
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(outer, this.orb.cX - outer.packedWidth / 2.0F, this.orb.cY - outer.packedHeight / 2.0F, outer.packedWidth / 2.0F, outer.packedHeight / 2.0F, outer.packedWidth, outer.packedHeight, this.scale, this.scaleY * Settings.scale,


                MathUtils.random(-2.0F, 2.0F));
        sb.draw(outer, this.orb.cX - outer.packedWidth / 2.0F, this.orb.cY - outer.packedHeight / 2.0F, outer.packedWidth / 2.0F, outer.packedHeight / 2.0F, outer.packedWidth, outer.packedHeight, this.scale, this.scaleY * Settings.scale,


                MathUtils.random(-2.0F, 2.0F));
        sb.setBlendFunction(770, 771);
        sb.setColor(this.color2);
        sb.draw(inner, this.orb.cX - inner.packedWidth / 2.0F, this.orb.cY - inner.packedHeight / 2.0F, inner.packedWidth / 2.0F, inner.packedHeight / 2.0F, inner.packedWidth, inner.packedHeight, this.scale * Settings.scale * 1.1F, this.scaleY * Settings.scale,


                MathUtils.random(-1.0F, 1.0F));
    }
}


