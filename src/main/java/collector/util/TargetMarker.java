package collector.util;

import collector.CollectorChar;
import collector.CollectorMod;
import collector.TorchChar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

public class TargetMarker {
    public Hitbox hb;
    private static UIStrings uiStrings = null;
    private static Texture targetTexture = null;
    private static Texture aggroCounterBgTexture = null;
    private static Color color = null;
    public static String[] TEXT = null;

    private static final int WIDTH = 128;
    private static final int HEIGHT = 128;

    private static final int FONT_BG_WIDTH = 48;
    private static final int FONT_BG_HEIGHT = 36;

    public float flashTimer;
    private Color flashColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);

    public TargetMarker() {
        hb = new Hitbox(WIDTH * Settings.scale * 1.2f, HEIGHT * Settings.scale * 1.2f);
        uiStrings = CardCrawlGame.languagePack.getUIString(CollectorMod.makeID("TargetMarker"));
        targetTexture = ImageMaster.loadImage("collectorResources/images/ui/Target.png");
        aggroCounterBgTexture = ImageMaster.loadImage("collectorResources/images/ui/AggroCounterBG.png");
        TEXT = uiStrings.TEXT;
        color = new Color(0xffdf8fff);
        flashTimer = 0;
    }

    public void update() {
        hb.update();

        if (this.flashTimer != 0.0F) {
            this.flashTimer -= Gdx.graphics.getDeltaTime();
            if (this.flashTimer < 0.0F) {
                this.flashTimer = 0.0F;
            }
        }
    }

    public void flash() {
        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            CardCrawlGame.sound.play("BUFF_1");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("BUFF_2");
        } else {
            CardCrawlGame.sound.play("BUFF_3");
        }
        this.flashTimer = 2.0f;
    }

    public void move(AbstractCreature creature) {
        float offset = 200.0f;
        if (creature instanceof TorchChar) {
            offset = ((TorchChar) creature).getTier() * 20.0f + 140.0f;
        }
        hb.move(creature.hb.cX, creature.hb.cY + offset * Settings.scale);
    }

    public void render(SpriteBatch sb) {
        sb.draw(
                targetTexture,
                hb.cX - WIDTH / 2.0f,
                hb.cY - HEIGHT / 2.0f,
                WIDTH / 2.0f,
                HEIGHT / 2.0f,
                WIDTH,
                HEIGHT,
                Settings.scale * 0.75f, Settings.scale * 0.75f, 0.0F, 0, 0, WIDTH, HEIGHT, false, false);

        sb.draw(
                aggroCounterBgTexture,
                hb.cX - FONT_BG_WIDTH / 2.0f,
                hb.cY - FONT_BG_HEIGHT / 2.0f + 70.0f * Settings.scale,
                FONT_BG_WIDTH / 2.0f,
                FONT_BG_HEIGHT / 2.0f,
                FONT_BG_WIDTH,
                FONT_BG_HEIGHT,
                Settings.scale, Settings.scale, 0.0F, 0, 0, FONT_BG_WIDTH, FONT_BG_HEIGHT, false, false);

        FontHelper.renderFontCentered(
                sb,
                FontHelper.panelNameFont,
                "+" + Math.abs(CollectorChar.getAggro()),
                hb.cX,
                hb.cY + 70.0f * Settings.scale,
                color);

        if (hb.hovered) {
            renderTip(CollectorChar.isSolo(), CollectorChar.getAggro());
        }

        hb.render(sb);
        renderFlash(sb);
    }

    public void renderFlash(SpriteBatch sb) {
        float tmp = Interpolation.exp10In.apply(0.0F, 3.0F, this.flashTimer / 2.0F);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        this.flashColor.a = this.flashTimer * 0.2F;// 1258
        sb.setColor(this.flashColor);

        sb.draw(targetTexture, hb.cX - WIDTH / 2.0f, hb.cY - HEIGHT / 2.0f, WIDTH / 2.0f, HEIGHT / 2.0f, WIDTH, HEIGHT,
                Settings.scale * (0.75f + tmp), Settings.scale * (0.75f + tmp), 0, 0, 0, WIDTH, HEIGHT, false, false);
        sb.draw(targetTexture, hb.cX - WIDTH / 2.0f, hb.cY - HEIGHT / 2.0f, WIDTH / 2.0f, HEIGHT / 2.0f, WIDTH, HEIGHT,
                Settings.scale * (0.75f + tmp * 0.66F), Settings.scale * (0.75f + tmp * 0.66F), 0, 0, 0, WIDTH, HEIGHT, false, false);
        sb.draw(targetTexture, hb.cX - WIDTH / 2.0f, hb.cY - HEIGHT / 2.0f, WIDTH / 2.0f, HEIGHT / 2.0f, WIDTH, HEIGHT,
                Settings.scale * (0.75f + tmp / 3.0F), Settings.scale * (0.75f + tmp / 3.0F), 0, 0, 0, WIDTH, HEIGHT, false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void renderTip(boolean solo, int aggro) {
        if (solo) {
            TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, TEXT[0], TEXT[5]);
        } else {
            if (CollectorChar.isFrontDragon()) {
                TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, TEXT[0], TEXT[3] + aggro + TEXT[4]);
            } else {
                TipHelper.renderGenericTip((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY, TEXT[0], TEXT[1]);
            }
        }
    }
}
