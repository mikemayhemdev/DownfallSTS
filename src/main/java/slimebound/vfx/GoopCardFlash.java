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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GoopCardFlash extends AbstractGameEffect {
    private AbstractCard card;
    private Texture img;
    private float yScale;
    private boolean isSuper;
    private boolean backwards;

    public GoopCardFlash(AbstractCard card, boolean isSuper) {
        this(card, new Color(0.3F, 0.2F, 0.3F, 0.0F), isSuper);
    }

    public GoopCardFlash(AbstractCard card, Color c, boolean isSuper) {
        this.yScale = 0.0F;
        this.isSuper = false;
        this.card = card;
        this.isSuper = isSuper;
        this.duration = 0.5F;
        if (isSuper) {
            this.img = ImageMaster.CARD_FLASH_VFX.getTexture();
        } else {
            switch (card.type) {
                case POWER:
                    this.img = ImageMaster.CARD_POWER_BG_SILHOUETTE.getTexture();
                    break;
                case ATTACK:
                    this.img = ImageMaster.CARD_ATTACK_BG_SILHOUETTE.getTexture();
                    break;
                default:
                    this.img = ImageMaster.CARD_SKILL_BG_SILHOUETTE.getTexture();
            }
        }

        this.color = c;
    }

    public GoopCardFlash(AbstractCard card) {
        this(card, new Color(1.0F, 0.8F, 0.2F, 0.0F), false);
    }

    public GoopCardFlash(AbstractCard card, Color c) {
        this.yScale = 0.0F;
        this.isSuper = false;
        this.card = card;
        this.duration = 0.5F;
        switch (card.type) {
            case POWER:
                this.img = ImageMaster.CARD_POWER_BG_SILHOUETTE.getTexture();
                break;
            case ATTACK:
                this.img = ImageMaster.CARD_ATTACK_BG_SILHOUETTE.getTexture();
                break;
            default:
                this.img = ImageMaster.CARD_SKILL_BG_SILHOUETTE.getTexture();
        }

        this.color = c;
        this.isSuper = false;
    }

    public void finish() {
        this.isDone = true;

    }

    public void update() {
        if (!this.backwards) {
            this.duration -= Gdx.graphics.getDeltaTime();

        } else {
            this.duration += Gdx.graphics.getDeltaTime();

        }

        this.yScale = Interpolation.bounceIn.apply(1.2F, 1.1F, this.duration * 2.0F);


        if (this.duration < 0.0F) {
            this.backwards = true;
        } else if (this.duration > 0.5F) {
            this.backwards = false;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        this.color.a = this.duration;
        sb.setColor(this.color);
        if (this.isSuper) {
            sb.draw(this.img, this.card.current_x - 256.0F, this.card.current_y - 400.0F, 256.0F, 400.0F, 512.0F, 800.0F, this.card.drawScale * (this.yScale + 1.0F) * 0.52F * Settings.scale, this.card.drawScale * (this.yScale + 1.0F) * 0.53F * Settings.scale, this.card.angle, 0, 0, 512, 800, false, false);
            sb.draw(this.img, this.card.current_x - 256.0F, this.card.current_y - 400.0F, 256.0F, 400.0F, 512.0F, 800.0F, this.card.drawScale * (this.yScale + 1.0F) * 0.55F * Settings.scale, this.card.drawScale * (this.yScale + 1.0F) * 0.57F * Settings.scale, this.card.angle, 0, 0, 512, 800, false, false);
            sb.draw(this.img, this.card.current_x - 256.0F, this.card.current_y - 400.0F, 256.0F, 400.0F, 512.0F, 800.0F, this.card.drawScale * (this.yScale + 1.0F) * 0.58F * Settings.scale, this.card.drawScale * (this.yScale + 1.0F) * 0.6F * Settings.scale, this.card.angle, 0, 0, 512, 800, false, false);
        } else {
            sb.draw(this.img, this.card.current_x - 256.0F, this.card.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, this.card.drawScale * (this.yScale + 1.0F) * 0.52F * Settings.scale, this.card.drawScale * (this.yScale + 1.0F) * 0.52F * Settings.scale, this.card.angle, 0, 0, 512, 512, false, false);
            sb.draw(this.img, this.card.current_x - 256.0F, this.card.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, this.card.drawScale * (this.yScale + 1.0F) * 0.55F * Settings.scale, this.card.drawScale * (this.yScale + 1.0F) * 0.55F * Settings.scale, this.card.angle, 0, 0, 512, 512, false, false);
            sb.draw(this.img, this.card.current_x - 256.0F, this.card.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, this.card.drawScale * (this.yScale + 1.0F) * 0.58F * Settings.scale, this.card.drawScale * (this.yScale + 1.0F) * 0.58F * Settings.scale, this.card.angle, 0, 0, 512, 512, false, false);
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
        this.img.dispose();
        this.isDone = true;
    }
}
