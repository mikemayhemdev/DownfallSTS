package collector.patches.ExtraDeckButtonPatches;

import basemod.TopPanelItem;
import collector.CollectorChar;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;

public class TopPanelExtraDeck extends TopPanelItem {

    public static final String ID = CollectorMod.makeID("CollectionUITopPanel");

    private static final Texture ICON = ImageMaster.DECK_ICON;
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public static DiscardPileViewScreen backup;

    public TopPanelExtraDeck() {
        super(ICON, ID);
        setClickable(true);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass == CollectorChar.Enums.THE_COLLECTOR && AbstractDungeon.floorNum > 1) {
            super.render(sb);
            if (getHitbox().hovered) {
                TipHelper.renderGenericTip(getHitbox().x, getHitbox().y, uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }

    @Override
    protected void onClick() {
        if (AbstractDungeon.floorNum > 1) {
            if (!CollectorCollection.collection.isEmpty() && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.DISCARD_VIEW && !(CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)) {
                CardCrawlGame.sound.play("STAB_BOOK_DEATH");
                CardGroup CardsToLookAt = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : CollectorCollection.collection.group) {
                    CardsToLookAt.addToTop(CardLibrary.getCopy(c.cardID));
                }

                AbstractDungeon.previousScreen = AbstractDungeon.screen;
                backup = AbstractDungeon.discardPileViewScreen;
                AbstractDungeon.discardPileViewScreen = new ViewCardScreen(CardsToLookAt.group);
                AbstractDungeon.discardPileViewScreen.open();
            }
        }
    }

    @Override
    protected void onHover() {
        super.onHover();
        CardCrawlGame.sound.play("UI_HOVER");
    }
}

