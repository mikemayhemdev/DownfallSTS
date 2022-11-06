package collector.patches.ExtraDeckButtonPatches;

import basemod.TopPanelItem;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import downfall.downfallMod;

public class TopPanelExtraDeck extends TopPanelItem {

    public static final String ID = CollectorMod.makeID("CollectionUITopPanel");

    private static final Texture ICON = ImageMaster.DECK_ICON;
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private static boolean isActive = false;

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


    @Override
    protected void onClick() {
        isActive = true;

    }


    @SpirePatch2(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class LoadBackupScreen {
        @SpirePrefixPatch
        public static void patch() {
            if (isActive) {

            }
        }
    }
}

