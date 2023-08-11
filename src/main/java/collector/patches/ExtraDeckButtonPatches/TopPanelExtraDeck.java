package collector.patches.ExtraDeckButtonPatches;

import basemod.ReflectionHacks;
import basemod.TopPanelItem;
import collector.CollectorChar;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.*;

public class TopPanelExtraDeck extends TopPanelItem {

    private static final float tipYpos = Settings.HEIGHT - (120.0f * Settings.scale);

    public static final String ID = CollectorMod.makeID("CollectionUITopPanel");

    private static final Texture ICON = ImageMaster.DECK_ICON;
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private static boolean isActive = false;
    private static CardGroup specialGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    private static final Set<AbstractDungeon.CurrentScreen> validScreens = new HashSet<>();

    static {
        validScreens.add(COMBAT_REWARD);
        validScreens.add(MASTER_DECK_VIEW);
        validScreens.add(DEATH);
        validScreens.add(BOSS_REWARD);
        validScreens.add(SHOP);
        validScreens.add(MAP);
    }

    public TopPanelExtraDeck() {
        super(ICON, ID);
    }

    @Override
    public boolean isClickable() {
        if (!AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR)) {
            return false;
        }
        return !AbstractDungeon.isScreenUp || (validScreens.contains(AbstractDungeon.screen) && !(AbstractDungeon.previousScreen == GRID || AbstractDungeon.previousScreen == BOSS_REWARD)) || isActive;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass == CollectorChar.Enums.THE_COLLECTOR) {
            render(sb, isClickable() ? Color.WHITE : Color.DARK_GRAY);
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelAmountFont, Integer.toString(CollectorCollection.collection.size()), this.x + 58.0F * Settings.scale, this.y + 25.0F * Settings.scale, Color.WHITE.cpy());
            if (getHitbox().hovered) {
                TipHelper.renderGenericTip(getHitbox().x, tipYpos, uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }


    private static void open() {
        specialGroup.clear();

        for (AbstractCard q : CollectorCollection.collection.group) {
            specialGroup.addToBottom(q);
        }

        isActive = true;
        CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
        AbstractDungeon.deckViewScreen.open();
    }

    @Override
    protected void onClick() {
        if (isClickable()) {
            if (isActive && AbstractDungeon.screen == MASTER_DECK_VIEW) {
                AbstractDungeon.closeCurrentScreen();
                CardCrawlGame.sound.play("DECK_CLOSE", 0.05F);
                isActive = false;
            } else {
                if (!AbstractDungeon.isScreenUp) {
                    open();
                    return;
                }

                switch (AbstractDungeon.screen) {
                    case COMBAT_REWARD:
                        AbstractDungeon.closeCurrentScreen();
                        AbstractDungeon.previousScreen = COMBAT_REWARD;
                        open();
                        break;
                    case MASTER_DECK_VIEW: //viewing master deck
                        AbstractDungeon.closeCurrentScreen();
                        open();
                        break;
                    case DEATH:
                        AbstractDungeon.deathScreen.hide();
                        AbstractDungeon.previousScreen = DEATH;
                        open();
                        break;
                    case BOSS_REWARD:
                        AbstractDungeon.bossRelicScreen.hide();
                        AbstractDungeon.previousScreen = BOSS_REWARD;
                        open();
                        break;
                    case SHOP:
                        AbstractDungeon.overlayMenu.cancelButton.hide();
                        AbstractDungeon.previousScreen = SHOP;
                        open();
                        break;
                    case MAP:
                        if (AbstractDungeon.dungeonMapScreen.dismissable) {
                            if (AbstractDungeon.previousScreen != null) {
                                AbstractDungeon.screenSwap = true;
                            }

                            AbstractDungeon.closeCurrentScreen();
                        } else { //non-dismissable map
                            AbstractDungeon.previousScreen = MAP;
                        }
                        open();
                        break;
                }
            }
        }
    }


    /*----- Patches -----*/

    @SpirePatch(
            clz = TopPanel.class,
            method = "updateDeckViewButtonLogic"
    )
    public static class DefinitelyNotViewingPool {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"clickedDeckButton"}
        )
        public static void viewingDeck(TopPanel __instance, boolean clickedDeckButton) {
            if (clickedDeckButton) {
                if (isActive && AbstractDungeon.screen == MASTER_DECK_VIEW) {
                    AbstractDungeon.closeCurrentScreen();
                }
                isActive = false;
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(InputAction.class, "isJustPressed");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "updateControllerInput"
    )
    public static class ControllerUseAlt {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"deck"}
        )
        public static void viewAlt(MasterDeckViewScreen __instance, @ByRef(type = "com.megacrit.cardcrawl.cards.CardGroup") Object[] deck) {
            if (isActive) {
                deck[0] = specialGroup;
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardGroup.class, "group");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "updatePositions"
    )
    public static class UpdateAltPositions {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"cards"}
        )
        public static void updateAlt(MasterDeckViewScreen __instance, @ByRef ArrayList<?>[] cards) {
            if (isActive) {
                cards[0] = specialGroup.group;
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "size");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "updateClicking"
    )
    public static class UpdateAltClick {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"hoveredCard", "clickStartedCard"}
        )
        public static SpireReturn<?> openAlt(MasterDeckViewScreen __instance, AbstractCard hovered, @ByRef(type = "com.megacrit.cardcrawl.cards.AbstractCard") Object[] clickStartedCard) {
            if (isActive) {
                CardCrawlGame.cardPopup.open(hovered, specialGroup);
                clickStartedCard[0] = null;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "open");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "calculateScrollBounds"
    )
    public static class CalcAltBounds {
        @SpirePrefixPatch
        public static SpireReturn<?> calcAlt(MasterDeckViewScreen __instance) {
            if (isActive) {
                if (specialGroup.size() > 10) {
                    int scrollTmp = specialGroup.size() / 5 - 2;
                    if (specialGroup.size() % 5 != 0) {
                        ++scrollTmp;
                    }

                    ReflectionHacks.setPrivate(__instance, MasterDeckViewScreen.class, "scrollUpperBound", Settings.DEFAULT_SCROLL_LIMIT + (float) scrollTmp * (AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y));
                } else {
                    ReflectionHacks.setPrivate(__instance, MasterDeckViewScreen.class, "scrollUpperBound", Settings.DEFAULT_SCROLL_LIMIT);
                }

                ReflectionHacks.setPrivate(__instance, MasterDeckViewScreen.class, "prevDeckSize", specialGroup.size());
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "hideCards"
    )
    public static class HideAltCards {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"cards"}
        )
        public static void hideAlt(MasterDeckViewScreen __instance, @ByRef ArrayList<?>[] cards) {
            if (isActive) {
                cards[0] = specialGroup.group;
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "size");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "render"
    )
    public static class RenderAlt {
        @SpirePrefixPatch
        public static SpireReturn<?> rendering(MasterDeckViewScreen __instance, SpriteBatch sb) {
            if (isActive) {
                AbstractCard hoveredCard = ReflectionHacks.getPrivate(__instance, MasterDeckViewScreen.class, "hoveredCard");
                if (hoveredCard == null) {
                    specialGroup.renderMasterDeck(sb);
                } else {
                    specialGroup.renderMasterDeckExceptOneCard(sb, hoveredCard);
                    hoveredCard.renderHoverShadow(sb);
                    hoveredCard.render(sb);
                }

                specialGroup.renderTip(sb);
                FontHelper.renderDeckViewTip(sb, uiStrings.TEXT[2], 96.0F * Settings.scale, Settings.CREAM_COLOR);
                if ((float) ReflectionHacks.getPrivate(__instance, MasterDeckViewScreen.class, "scrollUpperBound") > 500.0F * Settings.scale) {
                    ((ScrollBar) ReflectionHacks.getPrivate(__instance, MasterDeckViewScreen.class, "scrollBar")).render(sb);
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
