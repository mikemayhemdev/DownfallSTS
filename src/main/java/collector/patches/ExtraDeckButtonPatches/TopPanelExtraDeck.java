package collector.patches.ExtraDeckButtonPatches;

import basemod.TopPanelItem;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import downfall.downfallMod;

public class TopPanelExtraDeck extends TopPanelItem {

    public static final String ID = CollectorMod.makeID("CollectionUITopPanel");

    private static final Texture ICON = ImageMaster.DECK_ICON;
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private static boolean isActive = false;
    private static CardGroup playerDeck = null;

    public TopPanelExtraDeck() {
        super(ICON, ID);
        setClickable(true);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_COLLECTOR) {
            super.render(sb);
            if (getHitbox().hovered) {
                TipHelper.renderGenericTip(getHitbox().x, getHitbox().y, uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }

    @Override
    protected void onHover() {
        super.onHover();
        CardCrawlGame.sound.play("UI_HOVER");
    }

    private static void openView() {
        isActive = true;
        playerDeck = AbstractDungeon.player.masterDeck;
        AbstractDungeon.player.masterDeck = CollectorCollection.collection;
        AbstractDungeon.deckViewScreen.open();
    }

    @Override
    protected void onClick() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.closeCurrentScreen();
            openView();
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        } else if (!AbstractDungeon.isScreenUp) {
            openView();
        } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {
            AbstractDungeon.screenSwap = false;
            if (AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {
                AbstractDungeon.previousScreen = null;
            }

            AbstractDungeon.closeCurrentScreen();
            CardCrawlGame.sound.play("DECK_CLOSE", 0.05F);
        } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DEATH) {
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
            AbstractDungeon.deathScreen.hide();
            openView();
        } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.BOSS_REWARD;
            AbstractDungeon.bossRelicScreen.hide();
            openView();
        } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.SHOP;
            openView();
        } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && !AbstractDungeon.dungeonMapScreen.dismissable) {
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.MAP;
            openView();
        } else if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.SETTINGS && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.MAP) {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.INPUT_SETTINGS) {
                if (AbstractDungeon.previousScreen != null) {
                    AbstractDungeon.screenSwap = true;
                }

                AbstractDungeon.closeCurrentScreen();
                openView();
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.CARD_REWARD;
                AbstractDungeon.dynamicBanner.hide();
                openView();
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.GRID;
                AbstractDungeon.gridSelectScreen.hide();
                openView();
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.HAND_SELECT;
                openView();
            }
        } else {
            if (AbstractDungeon.previousScreen != null) {
                AbstractDungeon.screenSwap = true;
            }

            AbstractDungeon.closeCurrentScreen();
            openView();
        }

    }


    @SpirePatch2(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class LoadBackupScreen {
        @SpirePrefixPatch
        public static void patch() {
            if (isActive) {
                AbstractDungeon.player.masterDeck = playerDeck;
            }
        }
    }
}

