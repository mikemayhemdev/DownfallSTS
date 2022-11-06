package collector.patches.ExtraDeckButtonPatches;

import basemod.TopPanelItem;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import downfall.downfallMod;

public class TopPanelExtraDeck extends TopPanelItem {

    public static final String ID = CollectorMod.makeID("CollectionUITopPanel");

    private static final Texture ICON = ImageMaster.DECK_ICON;
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public static MasterDeckViewScreen backup;

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
    protected void onClick() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {
            AbstractDungeon.screenSwap = false;
            if (AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {
                AbstractDungeon.previousScreen = null;
            }

            AbstractDungeon.closeCurrentScreen();
            CardCrawlGame.sound.play("DECK_CLOSE", 0.05F);
        } else {
            CardCrawlGame.sound.play("STAB_BOOK_DEATH");
            CardGroup CardsToLookAt = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : CollectorCollection.collection.group) {
                CardsToLookAt.addToTop(CardLibrary.getCopy(c.cardID));
            }

            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            backup = AbstractDungeon.deckViewScreen;
            AbstractDungeon.deckViewScreen = new ViewCardScreen(CardsToLookAt.group);
            AbstractDungeon.deckViewScreen.open();
        }
    }

    @Override
    protected void onHover() {
        super.onHover();
        CardCrawlGame.sound.play("UI_HOVER");
    }
}

