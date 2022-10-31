package collector.patches.ExtraDeckButtonPatches;

import basemod.ReflectionHacks;
import collector.CollectorMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;

import java.util.ArrayList;

public class ViewCardScreen extends DiscardPileViewScreen {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(CollectorMod.makeID("CollectionScreen"));

    public boolean isHovered = false;
    private static final int CARDS_PER_LINE = 5;
    private boolean grabbedScreen = false;
    private static float drawStartX, drawStartY, padX, padY;
    private static final float SCROLL_BAR_THRESHOLD = 500f * Settings.scale;
    private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
    private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
    private float grabStartY = scrollLowerBound, currentDiffY = scrollLowerBound;
    private static final String HEADER_INFO = uiStrings.TEXT[1];
    private AbstractCard hoveredCard = null;
    private int prevDeckSize = 0;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard = null;

    private CardGroup cards;

    public ViewCardScreen(ArrayList<AbstractCard> cards) {
        drawStartX = Settings.WIDTH;
        drawStartX -= CARDS_PER_LINE * AbstractCard.IMG_WIDTH * Settings.CARD_VIEW_SCALE;
        drawStartX -= (CARDS_PER_LINE - 1) * (Settings.CARD_VIEW_PAD_X);
        drawStartX /= 2f;
        drawStartX += (AbstractCard.IMG_WIDTH * Settings.CARD_VIEW_SCALE) / 2f;

        padX = AbstractCard.IMG_WIDTH * Settings.CARD_VIEW_SCALE + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * Settings.CARD_VIEW_SCALE + Settings.CARD_VIEW_PAD_Y;
        scrollBar = new ScrollBar(this);
        scrollBar.changeHeight(Settings.HEIGHT - (384 * Settings.scale));

        this.cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cards.forEach(c -> this.cards.addToTop(c));
    }

    public void update() {
        updateControllerInput();
        if (Settings.isControllerMode && controllerCard != null && !CardCrawlGame.isPopupOpen && !CInputHelper
                .isTopPanelActive()) {
            if (Gdx.input.getY() > Settings.HEIGHT * 0.7f) {
                currentDiffY += Settings.SCROLL_SPEED;
            } else if (Gdx.input.getY() < Settings.HEIGHT * 0.3f) {
                currentDiffY -= Settings.SCROLL_SPEED;
            }
        }

        boolean isDraggingScrollBar = false;
        if (shouldShowScrollBar()) {
            isDraggingScrollBar = scrollBar.update();
        }
        if (!isDraggingScrollBar) {
            updateScrolling();
        }
        updatePositions();
        if (Settings.isControllerMode && controllerCard != null && !CInputHelper.isTopPanelActive()) {
            CInputHelper.setCursor(controllerCard.hb);
        }
    }

    private void updateControllerInput() {
        if (!Settings.isControllerMode || CInputHelper.isTopPanelActive()) {
            return;
        }

        boolean anyHovered = false;
        int index = 0;

        for (AbstractCard c : cards.group) {
            if (c.hb.hovered) {
                anyHovered = true;
                break;
            }
            index++;
        }

        if (!anyHovered) {
            Gdx.input.setCursorPosition(
                    (int) cards.group.get(0).hb.cX,
                    Settings.HEIGHT - (int) cards.group.get(0).hb.cY);
            controllerCard = cards.group.get(0);
        } else {
            if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                    && cards.size() > CARDS_PER_LINE) {
                // Wrap to bottom
                index -= CARDS_PER_LINE;
                if (index < 0) {
                    int wrap = cards.size() / CARDS_PER_LINE;
                    index += wrap * CARDS_PER_LINE;
                    if (index + CARDS_PER_LINE < cards.size()) {
                        index += CARDS_PER_LINE;
                    }
                }
                Gdx.input.setCursorPosition(
                        (int) cards.group.get(index).hb.cX,
                        Settings.HEIGHT - (int) cards.group.get(index).hb.cY);
                controllerCard = cards.group.get(index);
            } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                    && cards.size() > CARDS_PER_LINE) {
                if (index < cards.size() - CARDS_PER_LINE) {
                    index += CARDS_PER_LINE;
                } else {
                    index = index % CARDS_PER_LINE;
                }
                Gdx.input.setCursorPosition(
                        (int) cards.group.get(index).hb.cX,
                        Settings.HEIGHT - (int) cards.group.get(index).hb.cY);
                controllerCard = cards.group.get(index);
            } else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
                if (index % CARDS_PER_LINE > 0) {
                    index--;
                } else {
                    index += (CARDS_PER_LINE - 1);
                    if (index > cards.size() - 1) {
                        index = cards.size() - 1;
                    }
                }
                Gdx.input.setCursorPosition(
                        (int) cards.group.get(index).hb.cX,
                        Settings.HEIGHT - (int) cards.group.get(index).hb.cY);
                controllerCard = cards.group.get(index);
            } else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                if (index % CARDS_PER_LINE < (CARDS_PER_LINE - 1)) {
                    index++;
                    if (index > cards.size() - 1) {
                        index -= (cards.size() % CARDS_PER_LINE);
                    }
                } else {
                    index -= (CARDS_PER_LINE - 1);
                    if (index < 0) {
                        index = 0;
                    }
                }
                Gdx.input.setCursorPosition(
                        (int) cards.group.get(index).hb.cX,
                        Settings.HEIGHT - (int) cards.group.get(index).hb.cY);
                controllerCard = cards.group.get(index);
            }
        }
    }

    /**
     * Updates the positions of the cards
     */
    private void updatePositions() {
        hoveredCard = null;
        int lineNum = 0;
        for (int i = 0; i < cards.size(); i++) {
            int mod = i % CARDS_PER_LINE;
            if (mod == 0 && i != 0) {
                lineNum++;
            }
            cards.group.get(i).target_x = drawStartX + (mod * padX);
            cards.group.get(i).target_y = drawStartY + currentDiffY - (lineNum * padY);
            cards.group.get(i).update();

            if (AbstractDungeon.topPanel.potionUi.isHidden) {
                cards.group.get(i).updateHoverLogic();
                if (cards.group.get(i).hb.hovered) {
                    hoveredCard = cards.group.get(i);
                }
            }
        }
    }

    private void updateScrolling() {
        int y = InputHelper.mY;

        if (!grabbedScreen) {
            if (InputHelper.scrolledDown) {
                currentDiffY += Settings.SCROLL_SPEED;
            } else if (InputHelper.scrolledUp) {
                currentDiffY -= Settings.SCROLL_SPEED;
            }

            if (InputHelper.justClickedLeft) {
                grabbedScreen = true;
                grabStartY = y - currentDiffY;
            }
        } else {
            if (InputHelper.isMouseDown) {
                currentDiffY = y - grabStartY;
            } else {
                grabbedScreen = false;
            }
        }

        if (prevDeckSize != cards.size()) {
            calculateScrollBounds();
        }
        resetScrolling();
        updateBarPosition();
    }

    /**
     * Calculate our top and low bounds
     */
    private void calculateScrollBounds() {
        int scrollTmp = 0;
        if (cards.size() > CARDS_PER_LINE * 2) {
            scrollTmp = cards.size() / CARDS_PER_LINE - 2;
            if (cards.size() % CARDS_PER_LINE != 0) {
                scrollTmp++;
            }
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * padY;
        } else {
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }

        prevDeckSize = cards.size();
    }

    /**
     * Bounce back logic
     */
    private void resetScrolling() {
        if (currentDiffY < scrollLowerBound) {
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollLowerBound);
        } else if (currentDiffY > scrollUpperBound) {
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollUpperBound);
        }
    }

    public void reopen() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            controllerCard = null;
        }
        AbstractDungeon.overlayMenu.cancelButton.show(uiStrings.TEXT[0]);
    }

    public void open() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            controllerCard = null;
        }
        CardCrawlGame.sound.play("DECK_OPEN");
        AbstractDungeon.overlayMenu.showBlackScreen();
        currentDiffY = scrollLowerBound;
        grabStartY = scrollLowerBound;
        grabbedScreen = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = CurrentScreen.DISCARD_VIEW;
        for (AbstractCard c : cards.group) {
            c.setAngle(0f, true);
            c.targetDrawScale = Settings.CARD_VIEW_SCALE;
            c.drawScale = Settings.CARD_VIEW_SCALE;
            c.lighten(true);
        }
        hideCards();
        AbstractDungeon.overlayMenu.cancelButton.show("Cancel");

        if (cards.size() <= CARDS_PER_LINE) {
            drawStartY = Settings.HEIGHT * 0.5f;
        } else {
            drawStartY = Settings.HEIGHT * 0.66f;
        }

        calculateScrollBounds();
    }

    private void hideCards() {
        int lineNum = 0;
        for (int i = 0; i < cards.size(); i++) {
            int mod = i % CARDS_PER_LINE;
            if (mod == 0 && i != 0) {
                lineNum++;
            }
            cards.group.get(i).current_x = drawStartX + (mod * padX);
            cards.group.get(i).current_y = drawStartY + currentDiffY - (lineNum * padY) - MathUtils.random(
                    100f * Settings.scale,
                    200f * Settings.scale);
        }
    }

    public void render(SpriteBatch sb) {
        if (shouldShowScrollBar()) {
            scrollBar.render(sb);
        }

        if (hoveredCard == null) {
            cards.render(sb);
        } else {
            cards.renderExceptOneCard(sb, hoveredCard);
            hoveredCard.renderHoverShadow(sb);
            hoveredCard.render(sb);
            hoveredCard.renderCardTip(sb);
        }

        sb.setColor(Color.WHITE);

        FontHelper.renderDeckViewTip(sb, HEADER_INFO, 96f * Settings.scale, Settings.CREAM_COLOR);
    }

    @Override
    public void scrolledUsingBar(float newPercent) {
        currentDiffY = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        updateBarPosition();
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, currentDiffY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar() {
        return scrollUpperBound > SCROLL_BAR_THRESHOLD;
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class LoadBackupScreen {
        @SpirePrefixPatch
        public static void patch() {
            if (TopPanelExtraDeck.backup != null) {
                AbstractDungeon.discardPileViewScreen = TopPanelExtraDeck.backup;
                TopPanelExtraDeck.backup = null;
            }
        }
    }

    @SpirePatch2(clz = CancelButton.class, method = "update")
    public static class FixRemnant {
        @SpirePrefixPatch
        public static void patch(CancelButton __instance) {
            if (AbstractDungeon.discardPileViewScreen instanceof ViewCardScreen && (__instance.hb.clicked || ((InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed())))) {
                AbstractDungeon.closeCurrentScreen();
            }
        }
    }
}

