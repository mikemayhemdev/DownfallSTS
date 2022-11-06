package collector.patches.ExtraDeckButtonPatches;

import collector.CollectorMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;

import java.util.ArrayList;

public class ViewCardScreen extends MasterDeckViewScreen {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(CollectorMod.makeID("CollectionScreen"));
    private static CardGroup cards;

    public ViewCardScreen(ArrayList<AbstractCard> cards) {
        this.cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cards.forEach(c -> this.cards.addToTop(c));
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class LoadBackupScreen {
        @SpirePrefixPatch
        public static void patch() {
            if (TopPanelExtraDeck.backup != null) {
                AbstractDungeon.deckViewScreen = TopPanelExtraDeck.backup;
                TopPanelExtraDeck.backup = null;
            }
        }
    }

    @SpirePatch2(clz = CancelButton.class, method = "update")
    public static class FixRemnant {
        @SpirePrefixPatch
        public static void patch(CancelButton __instance) {
            if (AbstractDungeon.deckViewScreen instanceof ViewCardScreen && (__instance.hb.clicked || ((InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed())))) {
                AbstractDungeon.closeCurrentScreen();
            }
        }
    }
}

