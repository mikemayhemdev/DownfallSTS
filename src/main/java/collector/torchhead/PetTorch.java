package collector.torchhead;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;

public class PetTorch {

    private static final float WIDTH = 500F;
    private static final float HEIGHT = 500F;

    private int damage;
    private int hits;

    private int hp;
    private int maxHp;

    private Hitbox hb;
    public Hitbox healthHb;
    public Hitbox intentHb;
    private float hbShowTimer;
    private float healthHideTimer = 0.0F;
    private float healthBarAnimTimer = 0.0F;
    private float healthBarWidth;
    private float targetHealthBarWidth;
    private Color hbBgColor;
    private Color blockColor;
    private Color hbShadowColor;
    private Color blockOutlineColor;
    private Color redHbBarColor;
    private Color orangeHbBarColor;
    private Color blueHbBarColor;
    private float hbYOffset;
    private Color hbTextColor;
    private Color blockTextColor;
    private float blockScale = 1.0F;
    private float blockOffset = 0.0F;
    public float hbAlpha = 0.0F;

    public PetTorch() {
        this.hbBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.hb = new Hitbox(WIDTH, HEIGHT);
        this.intentHb = new Hitbox(64f * Settings.scale, 64f * Settings.scale);
    }

    public void update() {
        this.updateHealthBar();
    }

    public void render(SpriteBatch sb) {
        this.hb.render(sb);
        this.intentHb.render(sb);
        this.healthHb.render(sb);
        renderHealth(sb);
    }

    public void renderHealth(SpriteBatch sb) {
        if (!Settings.hideCombatElements) {
            float x = this.hb.cX - this.hb.width / 2.0F;
            float y = this.hb.cY - this.hb.height / 2.0F + this.hbYOffset;
            this.renderHealthBg(sb, x, y);
            if (this.targetHealthBarWidth != 0.0F) {
                this.renderOrangeHealthBar(sb, x, y);
                this.renderRedHealthBar(sb, x, y);
            }

            if (AbstractDungeon.player.currentBlock != 0 && this.hbAlpha != 0.0F) {
                this.renderBlockOutline(sb, x, y);
            }

            this.renderHealthText(sb, y);

            if (AbstractDungeon.player.currentBlock != 0 && this.hbAlpha != 0.0F) {
                this.renderBlockIconAndValue(sb, x, y);
            }
        }
    }

    private void renderHealthBg(SpriteBatch sb, float x, float y) {
        sb.setColor(this.hbShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - HEALTH_BAR_HEIGHT, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, this.hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.hb.width, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setColor(this.hbBgColor);
        if (this.hp != this.maxHp) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.hb.width, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }
    }

    private void renderOrangeHealthBar(SpriteBatch sb, float x, float y) {
        sb.setColor(this.orangeHbBarColor);
        sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.healthBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.healthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderRedHealthBar(SpriteBatch sb, float x, float y) {
        if (AbstractDungeon.player.currentBlock > 0) {
            sb.setColor(this.blueHbBarColor);
        } else {
            sb.setColor(this.redHbBarColor);
        }

        if (this.hp > 0) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }

        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.targetHealthBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderBlockOutline(SpriteBatch sb, float x, float y) {
        sb.setColor(this.blockOutlineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.BLOCK_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_R, x + this.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    private void renderHealthText(SpriteBatch sb, float y) {
        if (this.targetHealthBarWidth != 0.0F) {
            float tmp = this.hbTextColor.a;
            Color var10000 = this.hbTextColor;
            var10000.a *= this.healthHideTimer;
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.hp + "/" + this.maxHp, this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * Settings.scale, this.hbTextColor);
            this.hbTextColor.a = tmp;
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, "abc", this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y - 1.0F * Settings.scale, this.hbTextColor);
        }
    }

    private void renderBlockIconAndValue(SpriteBatch sb, float x, float y) {
        sb.setColor(this.blockColor);
        sb.draw(ImageMaster.BLOCK_ICON, x + BLOCK_ICON_X - 32.0F, y + BLOCK_ICON_Y - 32.0F + this.blockOffset, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(AbstractDungeon.player.currentBlock), x + BLOCK_ICON_X, y - 16.0F * Settings.scale, this.blockTextColor, this.blockScale);
    }

    protected void updateHealthBar() {
        this.updateHbHoverFade();
        this.updateHbPopInAnimation();
        this.updateHbDamageAnimation();
        this.updateHbAlpha();
    }


    public void healthBarUpdatedEvent() {
        this.healthBarAnimTimer = 1.2F;
        this.targetHealthBarWidth = this.hb.width * (float) this.hp / (float) this.maxHp;
        if (this.maxHp == this.hp) {
            this.healthBarWidth = this.targetHealthBarWidth;
        } else if (this.hp == 0) {
            this.healthBarWidth = 0.0F;
            this.targetHealthBarWidth = 0.0F;
        }

        if (this.targetHealthBarWidth > this.healthBarWidth) {
            this.healthBarWidth = this.targetHealthBarWidth;
        }

    }

    private void updateHbDamageAnimation() {
        if (this.healthBarAnimTimer > 0.0F) {
            this.healthBarAnimTimer -= Gdx.graphics.getDeltaTime();
        }

        if (this.healthBarWidth != this.targetHealthBarWidth && this.healthBarAnimTimer <= 0.0F && this.targetHealthBarWidth < this.healthBarWidth) {
            this.healthBarWidth = MathHelper.uiLerpSnap(this.healthBarWidth, this.targetHealthBarWidth);
        }

    }

    private void updateHbHoverFade() {
        if (this.healthHb.hovered) {
            this.healthHideTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.healthHideTimer < 0.2F) {
                this.healthHideTimer = 0.2F;
            }
        } else {
            this.healthHideTimer += Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.healthHideTimer > 1.0F) {
                this.healthHideTimer = 1.0F;
            }
        }

    }

    public void healthBarRevivedEvent() {
        this.healthBarAnimTimer = 1.2F;
        this.targetHealthBarWidth = this.hb.width * (float) this.hp / (float) this.maxHp;
        this.healthBarWidth = this.targetHealthBarWidth;
        this.hbBgColor.a = 0.75F;
        this.hbShadowColor.a = 0.5F;
        this.hbTextColor.a = 1.0F;
    }

    private void updateHbAlpha() {
        if (this.targetHealthBarWidth == 0.0F && this.healthBarAnimTimer <= 0.0F) {
            this.hbShadowColor.a = MathHelper.fadeLerpSnap(this.hbShadowColor.a, 0.0F);
            this.hbBgColor.a = MathHelper.fadeLerpSnap(this.hbBgColor.a, 0.0F);
            this.hbTextColor.a = MathHelper.fadeLerpSnap(this.hbTextColor.a, 0.0F);
            this.blockOutlineColor.a = MathHelper.fadeLerpSnap(this.blockOutlineColor.a, 0.0F);
        } else {
            this.hbBgColor.a = this.hbAlpha * 0.5F;
            this.hbShadowColor.a = this.hbAlpha * 0.2F;
            this.hbTextColor.a = this.hbAlpha;
            this.orangeHbBarColor.a = this.hbAlpha;
            this.redHbBarColor.a = this.hbAlpha;
            this.blueHbBarColor.a = this.hbAlpha;
            this.blockOutlineColor.a = this.hbAlpha;
        }
    }

    public void showHealthBar() {
        this.hbShowTimer = 0.7F;
        this.hbAlpha = 0.0F;
    }

    private void updateHbPopInAnimation() {
        if (this.hbShowTimer > 0.0F) {
            this.hbShowTimer -= Gdx.graphics.getDeltaTime();
            if (this.hbShowTimer < 0.0F) {
                this.hbShowTimer = 0.0F;
            }

            this.hbAlpha = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - this.hbShowTimer / 0.7F);
            this.hbYOffset = Interpolation.exp10Out.apply(HB_Y_OFFSET_DIST * 5.0F, 0.0F, 1.0F - this.hbShowTimer / 0.7F);
        }

    }

    public void hideHealthBar() {
        this.hbAlpha = 0.0F;
    }

    private static final float BLOCK_OFFSET_DIST = 12.0F * Settings.scale;
    private static final float HB_Y_OFFSET_DIST = 12.0F * Settings.scale;
    private static final float BLOCK_ICON_X = -14.0F * Settings.scale;
    private static final float BLOCK_ICON_Y = -14.0F * Settings.scale;
    private static final float HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
    private static final float HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;
    private static final float HEALTH_TEXT_OFFSET_Y = 6.0F * Settings.scale;
    private static final float HEALTH_BG_OFFSET_X = 31.0F * Settings.scale;
}
