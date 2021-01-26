package theTodo.util;

import basemod.IUIElement;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theTodo.cards.AbstractEasyCard;

import java.util.function.Consumer;

public class PanelMinMaxSlider implements IUIElement {
    private static final float L_X;
    private static final float SLIDE_W;
    private Consumer<PanelMinMaxSlider> change;
    public Hitbox hb;
    public Hitbox bgHb;
    private float x;
    private float sliderX;
    private float handleX;
    private float y;
    private boolean sliderGrabbed = false;
    private String label;
    private String format;
    private float value;
    private float minValue;
    private float maxValue;

    public PanelMinMaxSlider(String lbl, float posX, float posY, float min, float max, float val, String format, Consumer<PanelMinMaxSlider> changeAction) {
        this.label = lbl;
        if (format != null && !format.isEmpty()) {
            this.format = format;
        } else {
            this.format = "%.2f";
        }

        this.minValue = min;
        this.maxValue = max;
        this.change = changeAction;
        if (posX == -1.0F) {
            posX = L_X;
        } else {
            posX *= Settings.scale;
        }

        this.x = posX;
        this.sliderX = this.x - 11.0F * Settings.scale;
        this.handleX = this.x + SLIDE_W;
        this.y = posY * Settings.scale;
        this.hb = new Hitbox(42.0F * Settings.scale, 38.0F * Settings.scale);
        this.bgHb = new Hitbox(300.0F * Settings.scale, 38.0F * Settings.scale);
        this.bgHb.move(this.sliderX + SLIDE_W / 2.0F, this.y);
        this.setValue(val);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.OPTION_SLIDER_BG, this.sliderX, this.y - 12.0F, 0.0F, 12.0F, 250.0F, 24.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 250, 24, false, false);
        sb.draw(ImageMaster.OPTION_SLIDER, this.handleX - 22.0F, this.y - 22.0F, 22.0F, 22.0F, 44.0F, 44.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 44, 44, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, this.label, this.sliderX - 55.0F * Settings.scale, this.y, Color.WHITE);
        String renderVal = String.format(this.format, this.getValue());
        if (this.sliderGrabbed) {
            FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, renderVal, this.sliderX + SLIDE_W + 55.0F * Settings.scale, this.y, Settings.GREEN_TEXT_COLOR);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, renderVal, this.sliderX + SLIDE_W + 55.0F * Settings.scale, this.y, Settings.BLUE_TEXT_COLOR);
        }

        this.hb.render(sb);
        this.bgHb.render(sb);
    }

    public void update() {
        this.hb.update();
        this.bgHb.update();
        this.hb.move(this.handleX, this.y);
        if (this.sliderGrabbed) {
            if (InputHelper.isMouseDown) {
                this.handleX = MathHelper.fadeLerpSnap(this.handleX, (float) InputHelper.mX);
                this.handleX = Math.min(this.sliderX + SLIDE_W + 11.0F * Settings.scale, Math.max(this.handleX, this.sliderX + 11.0F * Settings.scale));
            } else {
                this.sliderGrabbed = false;
            }
        } else if (InputHelper.justClickedLeft && (this.hb.hovered || this.bgHb.hovered)) {
            this.sliderGrabbed = true;
        }

        float oldVal = this.getValue();
        this.value = (this.handleX - 11.0F * Settings.scale - this.sliderX) / SLIDE_W;
        if (oldVal != this.getValue()) {
            this.onChange();
        }

    }

    public float getValue() {
        return this.value * (this.maxValue - this.minValue) + this.minValue;
    }

    public void setValue(float val) {
        if (val < this.minValue) {
            val = this.minValue;
        } else if (val > this.maxValue) {
            val = this.maxValue;
        }

        val = (val - this.minValue) / (this.maxValue - this.minValue);
        this.value = val;
        this.handleX = this.sliderX + SLIDE_W * val + 11.0F * Settings.scale;
    }

    private void onChange() {
        this.change.accept(this);
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }

    public void set(float xPos, float yPos) {
        xPos *= Settings.scale;
        this.handleX += xPos - this.x;
        this.x = xPos;
        this.sliderX = this.x - 11.0F * Settings.scale;
        this.y = yPos * Settings.scale;
        this.bgHb.move(this.sliderX + SLIDE_W / 2.0F, this.y);
    }

    public void setX(float xPos) {
        this.set(xPos, this.y / Settings.scale);
    }

    public void setY(float yPos) {
        this.set(this.x / Settings.scale, yPos);
    }

    public float getX() {
        return this.x / Settings.scale;
    }

    public float getY() {
        return this.y / Settings.scale;
    }

    public void reInitAmount(char type) {
        setValue(getCorrectAmount(type));
    }

    public static float getCorrectAmount(char type) {
        AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
        if (q instanceof AbstractEasyCard) {
            switch (type) {
                case 'h':
                    return CardArtRoller.infos.get(q.cardID).H;
                case 's':
                    return CardArtRoller.infos.get(q.cardID).S;
                case 'c':
                    return CardArtRoller.infos.get(q.cardID).C;
                case 'l':
                    return CardArtRoller.infos.get(q.cardID).L;
            }
        }
        return 0.5f;
    }

    static {
        L_X = 1235.0F * Settings.scale;
        SLIDE_W = 230.0F * Settings.scale;
    }
}
