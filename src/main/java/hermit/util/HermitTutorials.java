package hermit.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;

import static com.megacrit.cardcrawl.helpers.input.InputActionSet.releaseCard;

public class HermitTutorials extends FtueTip {

    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("HermitMod:HermitTutorials");
    public static final String[] txt = tutorialStrings.TEXT;
    public static final String[] LABEL = tutorialStrings.LABEL;
    private Texture img1;
    private Texture img2;
    private Texture img3;
    private Color screen = Color.valueOf("1c262a00");
    private float x;
    private float x1;
    private float x2;
    private float x3;
    private float targetX;
    private float startX;
    private float scrollTimer = 0.0F;
    private int currentSlot = 0;
    private static String txt1 = txt[0];
    private static String txt2 = txt[1];

    private int closeScreen;


    public HermitTutorials() {

        this.img1 = ImageMaster.loadImage("hermitResources/images/tip/t1.png");
        this.img2 = ImageMaster.loadImage("hermitResources/images/tip/t2.png");

        txt1 = txt[0];
        txt2 = txt[1];
        this.closeScreen = -1;

        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.x = 0.0F;
        this.x1 = 567.0F * Settings.scale;
        this.x2 = this.x1 + Settings.WIDTH;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }


    public void update() {
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
        if (this.currentSlot <= this.closeScreen) {
            AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
        }
        if (this.screen.a != 0.8F) {
            this.screen.a += Gdx.graphics.getDeltaTime();
            if (this.screen.a > 0.8F) {
                this.screen.a = 0.8F;
            }
        }
        if ((AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft) || CInputActionSet.proceed
                .isJustPressed()) {

            CInputActionSet.proceed.unpress();
            if (this.currentSlot <= this.closeScreen) {

                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();

            }


            this.currentSlot--;
            this.startX = this.x;
            this.targetX = (this.currentSlot * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentSlot <= this.closeScreen) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
            }
        }
        if (this.scrollTimer != 0.0F) {
            this.scrollTimer -= Gdx.graphics.getDeltaTime();
            if (this.scrollTimer < 0.0F) {
                this.scrollTimer = 0.0F;
            }
        }
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(this.img1, this.x + this.x1 - 380.0F, Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        /*     */
        sb.draw(this.img2, this.x + this.x2 - 380.0F, Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        /*     */
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, txt1, this.x + this.x1 + 400.0F * Settings.scale, Settings.HEIGHT / 2.0F -
                FontHelper.getSmartHeight(FontHelper.panelNameFont, txt1, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, txt2, this.x + this.x2 + 400.0F * Settings.scale, Settings.HEIGHT / 2.0F -
                FontHelper.getSmartHeight(FontHelper.panelNameFont, txt2, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + Integer.toString(Math.abs(this.currentSlot - 1)) + "/" +
                Math.abs(this.closeScreen - 1) + LABEL[4], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);

        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }


}
