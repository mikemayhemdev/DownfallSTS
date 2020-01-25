package guardian.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;

public class RelicPreviewButton extends LargeDialogOptionButton {
    private AbstractRelic r;
    private float x;
    private float y;
    private boolean isDisabled;
    private float animTimer;
    private static Color TEXT_ACTIVE_COLOR = Color.WHITE.cpy();
    private static Color TEXT_INACTIVE_COLOR = new Color(0.8F, 0.8F, 0.8F, 1.0F);
    private static Color TEXT_DISABLED_COLOR = Color.FIREBRICK.cpy();
    private Color boxInactiveColor;
    private Color textColor;
    private Color boxColor;

    public RelicPreviewButton(int slot, String msg, AbstractRelic r, boolean isDisabled, AbstractCard previewCard) {
        super(slot, msg, isDisabled, previewCard);
        this.animTimer = 0.5F;
        this.textColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.boxColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.boxInactiveColor = new Color(0.2F, 0.25F, 0.25F, 0.0F);
        this.r = r;
        this.y = -9999.0F * Settings.scale;
        this.pressed = false;
        this.slot = 0;
        switch(AbstractEvent.type) {
            case TEXT:
                this.x = 895.0F * Settings.scale;
                break;
            case IMAGE:
                this.x = 1260.0F * Settings.scale;
                break;
            case ROOM:
                this.x = 620.0F * Settings.scale;
        }

        this.slot = slot;
        this.isDisabled = isDisabled;
        if (isDisabled) {
            this.msg = this.stripColor(msg);
        } else {
            this.msg = msg;
        }
        if (isDisabled) {
            this.textColor = TEXT_DISABLED_COLOR;
            this.boxColor = this.boxInactiveColor;
        } else {
            this.hb = new Hitbox(892.0F * Settings.scale, 80.0F * Settings.scale);
        }

    }

    public void renderRelicPreview(SpriteBatch sb) {
        if (this.r != null && this.hb.hovered) {
            this.r.currentX = this.x + this.hb.width / 1.75F;
            if (this.y < this.r.hb.height / 2.0F + 5.0F) {
                this.y = this.r.hb.height / 2.0F + 5.0F;
            }

            this.r.currentY = this.y;
            TipHelper.queuePowerTips((float)InputHelper.mX - 350.0F * Settings.scale, Settings.HEIGHT/3F, r.tips);
        }
    }

    @Override
    public void update(int size) {
        super.update(size);
        this.hoverAndClickLogic();
    }

    @SpireOverride
    private void hoverAndClickLogic() {
        this.hb.update();
        if (this.hb.hovered && InputHelper.justClickedLeft && !this.isDisabled && this.animTimer < 0.1F) {
            InputHelper.justClickedLeft = false;
            this.hb.clickStarted = true;
        }

        if (this.hb.hovered && CInputActionSet.select.isJustPressed() && !this.isDisabled) {
            this.hb.clicked = true;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.pressed = true;
        }

        if (!this.isDisabled) {
            if (this.hb.hovered) {
                this.textColor = TEXT_ACTIVE_COLOR;
                this.boxColor = Color.WHITE.cpy();
            } else {
                this.textColor = TEXT_INACTIVE_COLOR;
                this.boxColor = new Color(0.4F, 0.4F, 0.4F, 1.0F);
            }
        }
        if (this.hb.hovered) {
            if (!this.isDisabled) {
                this.textColor = TEXT_ACTIVE_COLOR;
            } else {
                this.textColor = Color.GRAY.cpy();
            }
        } else if (!this.isDisabled) {
            this.textColor = TEXT_ACTIVE_COLOR;
        } else {
            this.textColor = Color.GRAY.cpy();
        }
    }

    private String stripColor(String input) {
        input = input.replace("#r", "");
        input = input.replace("#g", "");
        input = input.replace("#b", "");
        input = input.replace("#y", "");
        return input;
    }
}