package theTodo.util;

import basemod.IUIElement;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import java.util.function.Consumer;

public class NonModButton implements IUIElement {
    private static final float HB_SHRINK = 14.0F;
    private Consumer<NonModButton> click;
    public Hitbox hb;
    private Texture texture;
    private float x;
    private float y;
    private float w;
    private float h;

    public NonModButton(float xPos, float yPos, Consumer<NonModButton> c) {
        this(xPos, yPos, ImageMaster.loadImage("img/BlankButton.png"), c);
    }

    public NonModButton(float xPos, float yPos, Texture tex, Consumer<NonModButton> c) {
        this.texture = tex;
        this.x = xPos * Settings.scale;
        this.y = yPos * Settings.scale;
        this.w = (float)this.texture.getWidth();
        this.h = (float)this.texture.getHeight();
        this.hb = new Hitbox(this.x + 14.0F * Settings.scale, this.y + 14.0F * Settings.scale, (this.w - 28.0F) * Settings.scale, (this.h - 28.0F) * Settings.scale);
        this.click = c;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.texture, this.x, this.y, this.w * Settings.scale, this.h * Settings.scale);
        this.hb.render(sb);
    }

    public void update() {
        this.hb.update();
        if (this.hb.justHovered) {
            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
        }

        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
            this.hb.clickStarted = true;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.onClick();
        }

    }

    private void onClick() {
        this.click.accept(this);
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }
}
