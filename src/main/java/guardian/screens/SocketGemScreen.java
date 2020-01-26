package guardian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class SocketGemScreen implements ScrollBarListener {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final int CARDS_PER_LINE = 5;
    private static final float SCROLL_BAR_THRESHOLD;
    private float grabStartY = 0.0F;
    private float currentDiffY = 0.0F;
    public ArrayList<AbstractCard> selectedCards = new ArrayList();
    private CardGroup targetGroup;
    private AbstractCard hoveredCard = null;
    public AbstractCard upgradePreviewCard = null;
    private int numCards = 0;
    private int cardSelectAmount = 0;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private boolean grabbedScreen;
    private boolean canCancel;
    public boolean forGem;
    public boolean forSocket;
    public boolean confirmScreenUp;
    public boolean isJustForConfirming;
    public GridSelectConfirmButton confirmButton;
    private String tipMsg;
    private String lastTip;
    private int prevDeckSize;
    public boolean cancelWasOn;
    public boolean anyNumber;
    public String cancelText;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private static final int ARROW_W = 64;
    private float arrowScale1;
    private float arrowScale2;
    private float arrowScale3;
    private float arrowTimer;

    public SocketGemScreen() {
        this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        this.grabbedScreen = false;
        this.canCancel = true;
        this.forGem = false;
        this.forSocket = false;
        this.confirmScreenUp = false;
        this.isJustForConfirming = false;
        this.confirmButton = new GridSelectConfirmButton(TEXT[0]);
        this.tipMsg = "";
        this.lastTip = "";
        this.prevDeckSize = 0;
        this.cancelWasOn = false;
        this.anyNumber = false;
        this.controllerCard = null;
        this.arrowScale1 = 1.0F;
        this.arrowScale2 = 1.0F;
        this.arrowScale3 = 1.0F;
        this.arrowTimer = 0.0F;
        drawStartX = (float)Settings.WIDTH;
        drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        this.scrollBar = new ScrollBar(this);
        this.scrollBar.move(0.0F, -30.0F * Settings.scale);
    }

    public void update() {
        this.updateControllerInput();
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen) {
            if ((float) Gdx.input.getY() > (float)Settings.HEIGHT * 0.75F) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            } else if ((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.25F) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
        }

        boolean isDraggingScrollBar = false;
        if (this.shouldShowScrollBar()) {
            isDraggingScrollBar = this.scrollBar.update();
        }

        if (!isDraggingScrollBar) {
            this.updateScrolling();
        }

        this.confirmButton.update();
        Iterator var2;
        AbstractCard c;
        if (this.isJustForConfirming) {
            this.updateCardPositionsAndHoverLogic();
            if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.dynamicBanner.hide();
                this.confirmScreenUp = false;
                var2 = this.targetGroup.group.iterator();

                while(var2.hasNext()) {
                    c = (AbstractCard)var2.next();
                    AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, c.current_x, c.current_y));
                }

                AbstractDungeon.closeCurrentScreen();
            }

        } else if (this.anyNumber && this.confirmButton.hb.clicked) {
            this.confirmButton.hb.clicked = false;
            AbstractDungeon.closeCurrentScreen();
        } else {
            if (!this.confirmScreenUp) {
                this.updateCardPositionsAndHoverLogic();
                if (this.hoveredCard != null && InputHelper.justClickedLeft) {
                    this.hoveredCard.hb.clickStarted = true;
                }

                if (this.hoveredCard != null && (this.hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed())) {
                    this.hoveredCard.hb.clicked = false;
                    if (!this.selectedCards.contains(this.hoveredCard)) {
                        this.selectedCards.add(this.hoveredCard);
                        this.hoveredCard.beginGlowing();
                        this.hoveredCard.targetDrawScale = 0.75F;
                        this.hoveredCard.drawScale = 0.875F;
                        ++this.cardSelectAmount;
                        CardCrawlGame.sound.play("CARD_SELECT");
                        if (this.numCards == this.cardSelectAmount) {
                            if (this.forSocket) {
                                this.hoveredCard.untip();
                                this.confirmScreenUp = true;
                                this.upgradePreviewCard = this.hoveredCard.makeStatEquivalentCopy();
                                this.upgradePreviewCard.upgrade();
                                this.upgradePreviewCard.displayUpgrades();
                                this.upgradePreviewCard.drawScale = 0.875F;
                                this.hoveredCard.stopGlowing();
                                this.selectedCards.clear();
                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
                                this.confirmButton.show();
                                this.confirmButton.isDisabled = false;
                                this.lastTip = this.tipMsg;
                                this.tipMsg = TEXT[2];
                                return;
                            }

                            if (this.forGem) {
                                this.hoveredCard.untip();
                                this.hoveredCard.stopGlowing();
                                this.selectedCards.clear();
                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
                                this.lastTip = this.tipMsg;
                                this.tipMsg = TEXT[2];
                                return;
                            }

                            AbstractDungeon.closeCurrentScreen();
                            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.SHOP) {
                                AbstractDungeon.overlayMenu.cancelButton.hide();
                            } else {
                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[3]);
                            }

                            var2 = this.selectedCards.iterator();

                            while(var2.hasNext()) {
                                c = (AbstractCard)var2.next();
                                c.stopGlowing();
                            }

                            if (this.targetGroup.type == CardGroup.CardGroupType.DISCARD_PILE) {
                                var2 = this.targetGroup.group.iterator();

                                while(var2.hasNext()) {
                                    c = (AbstractCard)var2.next();
                                    c.drawScale = 0.12F;
                                    c.targetDrawScale = 0.12F;
                                    c.teleportToDiscardPile();
                                    c.lighten(true);
                                }
                            }

                            return;
                        }
                    } else if (this.selectedCards.contains(this.hoveredCard)) {
                        this.hoveredCard.stopGlowing();
                        this.selectedCards.remove(this.hoveredCard);
                        --this.cardSelectAmount;
                    }

                    return;
                }
            } else {
                if (this.forGem) {

                }

                if (this.forSocket) {
                    this.upgradePreviewCard.update();
                }


                if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                    CInputActionSet.select.unpress();
                    this.confirmButton.hb.clicked = false;
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                    this.confirmScreenUp = false;
                    this.selectedCards.add(this.hoveredCard);
                    AbstractDungeon.closeCurrentScreen();
                }
            }

            if (Settings.isControllerMode && this.controllerCard != null) {
                Gdx.input.setCursorPosition((int)this.controllerCard.hb.cX, (int)((float)Settings.HEIGHT - this.controllerCard.hb.cY));
            }

        }
    }

    private void updateControllerInput() {
        if (Settings.isControllerMode && this.upgradePreviewCard == null) {
            boolean anyHovered = false;
            int index = 0;

            for(Iterator var3 = this.targetGroup.group.iterator(); var3.hasNext(); ++index) {
                AbstractCard c = (AbstractCard)var3.next();
                if (c.hb.hovered) {
                    anyHovered = true;
                    break;
                }
            }

            if (!anyHovered) {
                Gdx.input.setCursorPosition((int)((AbstractCard)this.targetGroup.group.get(0)).hb.cX, (int)((AbstractCard)this.targetGroup.group.get(0)).hb.cY);
                this.controllerCard = (AbstractCard)this.targetGroup.group.get(0);
            } else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && this.targetGroup.size() > 5) {
                if (index < 5) {
                    index = this.targetGroup.size() + 2 - (4 - index);
                    if (index > this.targetGroup.size() - 1) {
                        index -= 5;
                    }

                    if (index > this.targetGroup.size() - 1 || index < 0) {
                        index = 0;
                    }
                } else {
                    index -= 5;
                }

                Gdx.input.setCursorPosition((int)((AbstractCard)this.targetGroup.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.targetGroup.group.get(index)).hb.cY);
                this.controllerCard = (AbstractCard)this.targetGroup.group.get(index);
            } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && this.targetGroup.size() > 5) {
                if (index < this.targetGroup.size() - 5) {
                    index += 5;
                } else {
                    index %= 5;
                }

                Gdx.input.setCursorPosition((int)((AbstractCard)this.targetGroup.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.targetGroup.group.get(index)).hb.cY);
                this.controllerCard = (AbstractCard)this.targetGroup.group.get(index);
            } else if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                    if (index % 5 < 4) {
                        ++index;
                        if (index > this.targetGroup.size() - 1) {
                            index -= this.targetGroup.size() % 5;
                        }
                    } else {
                        index -= 4;
                        if (index < 0) {
                            index = 0;
                        }
                    }

                    Gdx.input.setCursorPosition((int)((AbstractCard)this.targetGroup.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.targetGroup.group.get(index)).hb.cY);
                    this.controllerCard = (AbstractCard)this.targetGroup.group.get(index);
                }
            } else {
                if (index % 5 > 0) {
                    --index;
                } else {
                    index += 4;
                    if (index > this.targetGroup.size() - 1) {
                        index = this.targetGroup.size() - 1;
                    }
                }

                Gdx.input.setCursorPosition((int)((AbstractCard)this.targetGroup.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.targetGroup.group.get(index)).hb.cY);
                this.controllerCard = (AbstractCard)this.targetGroup.group.get(index);
            }

        }
    }

    private void updateCardPositionsAndHoverLogic() {
        if (this.isJustForConfirming && this.targetGroup.size() <= 4) {
            switch(this.targetGroup.size()) {
                case 1:
                    this.targetGroup.getBottomCard().current_x = (float)Settings.WIDTH / 2.0F;
                    this.targetGroup.getBottomCard().target_x = (float)Settings.WIDTH / 2.0F;
                    break;
                case 2:
                    ((AbstractCard)this.targetGroup.group.get(0)).current_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(1)).current_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                    break;
                case 3:
                    ((AbstractCard)this.targetGroup.group.get(0)).current_x = drawStartX + padX;
                    ((AbstractCard)this.targetGroup.group.get(1)).current_x = drawStartX + padX * 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).current_x = drawStartX + padX * 3.0F;
                    ((AbstractCard)this.targetGroup.group.get(0)).target_x = drawStartX + padX;
                    ((AbstractCard)this.targetGroup.group.get(1)).target_x = drawStartX + padX * 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).target_x = drawStartX + padX * 3.0F;
                    break;
                case 4:
                    ((AbstractCard)this.targetGroup.group.get(0)).current_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F - padX;
                    ((AbstractCard)this.targetGroup.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F - padX;
                    ((AbstractCard)this.targetGroup.group.get(1)).current_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).current_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                    ((AbstractCard)this.targetGroup.group.get(3)).current_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F + padX;
                    ((AbstractCard)this.targetGroup.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F + padX;
            }

            ArrayList<AbstractCard> c2 = this.targetGroup.group;

            for(int i = 0; i < c2.size(); ++i) {
                ((AbstractCard)c2.get(i)).target_y = drawStartY + this.currentDiffY;
                ((AbstractCard)c2.get(i)).fadingOut = false;
                ((AbstractCard)c2.get(i)).update();
                ((AbstractCard)c2.get(i)).updateHoverLogic();
                this.hoveredCard = null;
                Iterator var9 = c2.iterator();

                while(var9.hasNext()) {
                    AbstractCard c = (AbstractCard)var9.next();
                    if (c.hb.hovered) {
                        this.hoveredCard = c;
                    }
                }
            }

        } else {
            int lineNum = 0;
            ArrayList<AbstractCard> cards = this.targetGroup.group;

            for(int i = 0; i < cards.size(); ++i) {
                int mod = i % 5;
                if (mod == 0 && i != 0) {
                    ++lineNum;
                }

                ((AbstractCard)cards.get(i)).target_x = drawStartX + (float)mod * padX;
                ((AbstractCard)cards.get(i)).target_y = drawStartY + this.currentDiffY - (float)lineNum * padY;
                ((AbstractCard)cards.get(i)).fadingOut = false;
                ((AbstractCard)cards.get(i)).update();
                ((AbstractCard)cards.get(i)).updateHoverLogic();
                this.hoveredCard = null;
                Iterator var5 = cards.iterator();

                while(var5.hasNext()) {
                    AbstractCard c = (AbstractCard)var5.next();
                    if (c.hb.hovered) {
                        this.hoveredCard = c;
                    }
                }
            }

        }
    }

    public void open(CardGroup group, int numCards, String tipMsg, boolean forSocket, boolean forGem, boolean canCancel) {
        this.targetGroup = group;
        this.callOnOpen();
        this.forSocket = forSocket;
        this.forGem = forGem;
        this.canCancel = canCancel;
        this.tipMsg = tipMsg;
        this.numCards = numCards;
        if ((forSocket || forGem || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) && canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        }

        if (!canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.hide();
        }

        this.calculateScrollBounds();
    }

    public void open(CardGroup group, int numCards, String tipMsg, boolean forSocket, boolean forGem) {
        this.open(group, numCards, tipMsg, forSocket, forGem, true);
    }

    public void open(CardGroup group, int numCards, String tipMsg, boolean forSocket) {
        this.open(group, numCards, tipMsg, forSocket, false);
    }

    public void openConfirmationGrid(CardGroup group, String tipMsg) {
        this.targetGroup = group;
        this.callOnOpen();
        this.isJustForConfirming = true;
        this.tipMsg = tipMsg;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        this.confirmButton.show();
        this.confirmButton.updateText(TEXT[0]);
        this.confirmButton.isDisabled = false;
        this.canCancel = false;
        if (group.size() <= 5) {
            AbstractDungeon.dynamicBanner.appear(tipMsg);
        }

    }

    private void callOnOpen() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }

        this.anyNumber = false;
        this.canCancel = false;
        this.forGem = false;
        this.forSocket = false;
        this.confirmScreenUp = false;
        this.isJustForConfirming = false;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.controllerCard = null;
        this.hoveredCard = null;
        this.selectedCards.clear();
        AbstractDungeon.topPanel.unhoverHitboxes();
        this.cardSelectAmount = 0;
        this.currentDiffY = 0.0F;
        this.grabStartY = 0.0F;
        this.grabbedScreen = false;
        this.hideCards();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.GRID;
        AbstractDungeon.overlayMenu.showBlackScreen(0.5F);
        this.confirmButton.hideInstantly();
        if (this.targetGroup.group.size() <= 5) {
            drawStartY = (float)Settings.HEIGHT * 0.5F;
        } else {
            drawStartY = (float)Settings.HEIGHT * 0.66F;
        }

    }

    public void reopen() {
        AbstractDungeon.overlayMenu.showBlackScreen(0.5F);
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.GRID;
        AbstractDungeon.topPanel.unhoverHitboxes();
        if (this.cancelWasOn && !this.isJustForConfirming && this.canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(this.cancelText);
        }

        Iterator var1 = this.targetGroup.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.targetDrawScale = 0.75F;
            c.drawScale = 0.75F;
            c.lighten(false);
        }

        this.scrollBar.reset();
    }

    public void hide() {
        if (!AbstractDungeon.overlayMenu.cancelButton.isHidden) {
            this.cancelWasOn = true;
            this.cancelText = AbstractDungeon.overlayMenu.cancelButton.buttonText;
        }

    }

    private void updateScrolling() {
        if (this.isJustForConfirming && this.targetGroup.size() <= 5) {
            this.currentDiffY = -64.0F * Settings.scale;
        } else {
            int y = InputHelper.mY;
            boolean isDraggingScrollBar = this.scrollBar.update();
            if (!isDraggingScrollBar) {
                if (!this.grabbedScreen) {
                    if (InputHelper.scrolledDown) {
                        this.currentDiffY += Settings.SCROLL_SPEED;
                    } else if (InputHelper.scrolledUp) {
                        this.currentDiffY -= Settings.SCROLL_SPEED;
                    }

                    if (InputHelper.justClickedLeft) {
                        this.grabbedScreen = true;
                        this.grabStartY = (float)y - this.currentDiffY;
                    }
                } else if (InputHelper.isMouseDown) {
                    this.currentDiffY = (float)y - this.grabStartY;
                } else {
                    this.grabbedScreen = false;
                }
            }

            if (this.prevDeckSize != this.targetGroup.size()) {
                this.calculateScrollBounds();
            }

            this.resetScrolling();
            this.updateBarPosition();
        }
    }

    private void calculateScrollBounds() {
        if (this.targetGroup.size() > 10) {
            int scrollTmp = this.targetGroup.size() / 5 - 2;
            if (this.targetGroup.size() % 5 != 0) {
                ++scrollTmp;
            }

            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float)scrollTmp * padY;
        } else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }

        this.prevDeckSize = this.targetGroup.size();
    }

    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        } else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }

    }

    private void hideCards() {
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;

        for(int i = 0; i < cards.size(); ++i) {
            ((AbstractCard)cards.get(i)).setAngle(0.0F, true);
            int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }

            ((AbstractCard)cards.get(i)).lighten(true);
            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = drawStartY + this.currentDiffY - (float)lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
            ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)cards.get(i)).drawScale = 0.75F;
        }

    }

    public void cancelUpgrade() {
        this.cardSelectAmount = 0;
        this.confirmScreenUp = false;
        this.confirmButton.hide();
        this.confirmButton.isDisabled = true;
        this.hoveredCard = null;
        this.upgradePreviewCard = null;
        if (Settings.isControllerMode && this.controllerCard != null) {
            this.hoveredCard = this.controllerCard;
        }

        if ((this.forSocket || this.forGem || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) && this.canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        }

        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.targetGroup.group;

        for(int i = 0; i < cards.size(); ++i) {
            int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }

            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = drawStartY + this.currentDiffY - (float)lineNum * padY;
        }

        this.tipMsg = this.lastTip;
    }

    public void render(SpriteBatch sb) {
        if (this.shouldShowScrollBar()) {
            this.scrollBar.render(sb);
        }

        if (this.hoveredCard != null) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.targetGroup.renderExceptOneCard(sb, this.hoveredCard);
            } else {
                this.targetGroup.renderExceptOneCardShowBottled(sb, this.hoveredCard);
            }

            this.hoveredCard.renderHoverShadow(sb);
            this.hoveredCard.render(sb);
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
                AbstractRelic tmp;
                float prevX;
                float prevY;
                if (this.hoveredCard.inBottleFlame) {
                    tmp = RelicLibrary.getRelic("Bottled Flame");
                    prevX = tmp.currentX;
                    prevY = tmp.currentY;
                    tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                    tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                    tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                } else if (this.hoveredCard.inBottleLightning) {
                    tmp = RelicLibrary.getRelic("Bottled Lightning");
                    prevX = tmp.currentX;
                    prevY = tmp.currentY;
                    tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                    tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                    tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                } else if (this.hoveredCard.inBottleTornado) {
                    tmp = RelicLibrary.getRelic("Bottled Tornado");
                    prevX = tmp.currentX;
                    prevY = tmp.currentY;
                    tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                    tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                    tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                    tmp.render(sb);
                    tmp.currentX = prevX;
                    tmp.currentY = prevY;
                    tmp = null;
                }
            }

            this.hoveredCard.renderCardTip(sb);
        } else if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.targetGroup.render(sb);
        } else {
            this.targetGroup.renderShowBottled(sb);
        }

        if (this.confirmScreenUp) {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT - 64.0F * Settings.scale);
            if (!this.forGem && !this.forSocket) {
                this.hoveredCard.current_x = (float)Settings.WIDTH / 2.0F;
                this.hoveredCard.current_y = (float)Settings.HEIGHT / 2.0F;
                this.hoveredCard.render(sb);
                this.hoveredCard.updateHoverLogic();
            } else {
                this.renderArrows(sb);
                this.hoveredCard.current_x = (float)Settings.WIDTH * 0.36F;
                this.hoveredCard.current_y = (float)Settings.HEIGHT / 2.0F;
                this.hoveredCard.target_x = (float)Settings.WIDTH * 0.36F;
                this.hoveredCard.target_y = (float)Settings.HEIGHT / 2.0F;
                this.hoveredCard.render(sb);
                this.hoveredCard.updateHoverLogic();
                this.upgradePreviewCard.current_x = (float)Settings.WIDTH * 0.63F;
                this.upgradePreviewCard.current_y = (float)Settings.HEIGHT / 2.0F;
                this.upgradePreviewCard.target_x = (float)Settings.WIDTH * 0.63F;
                this.upgradePreviewCard.target_y = (float)Settings.HEIGHT / 2.0F;
                this.upgradePreviewCard.render(sb);
                this.upgradePreviewCard.updateHoverLogic();
                this.upgradePreviewCard.renderCardTip(sb);
            }
        }

        if (this.forSocket || this.forGem || this.isJustForConfirming || this.anyNumber) {
            this.confirmButton.render(sb);
        }

        if (!this.isJustForConfirming || this.targetGroup.size() > 5) {
            FontHelper.renderDeckViewTip(sb, this.tipMsg, 96.0F * Settings.scale, Settings.CREAM_COLOR);
        }

    }

    private void renderArrows(SpriteBatch sb) {
        float x = (float)Settings.WIDTH / 2.0F - 73.0F * Settings.scale - 32.0F;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale1 * Settings.scale, this.arrowScale1 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale2 * Settings.scale, this.arrowScale2 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale3 * Settings.scale, this.arrowScale3 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        this.arrowTimer += Gdx.graphics.getDeltaTime() * 2.0F;
        this.arrowScale1 = 0.8F + (MathUtils.cos(this.arrowTimer) + 1.0F) / 8.0F;
        this.arrowScale2 = 0.8F + (MathUtils.cos(this.arrowTimer - 0.8F) + 1.0F) / 8.0F;
        this.arrowScale3 = 0.8F + (MathUtils.cos(this.arrowTimer - 1.6F) + 1.0F) / 8.0F;
    }

    public void scrolledUsingBar(float newPercent) {
        this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        this.updateBarPosition();
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
        this.scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar() {
        return !this.confirmScreenUp && this.scrollUpperBound > SCROLL_BAR_THRESHOLD;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GridCardSelectScreen");
        TEXT = uiStrings.TEXT;
        SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;
    }
}
