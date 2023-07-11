package collector.ui;

import collector.CollectorCollection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.patches.UnknownExtraUiPatch;
import sneckomod.relics.UnknownEgg;

public class StashAwayCampfireEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("collector:StashAwayCampfireOption");
    public static final String[] TEXT = uiStrings.TEXT;

    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    public StashAwayCampfireEffect() {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }

        if (!AbstractDungeon.isScreenUp) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {

                    AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                            (AbstractCard) UnknownExtraUiPatch.parentCard.get(c), Settings.WIDTH * 0.35F, Settings.HEIGHT / 2));


                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
            } else if (this.openedScreen) {
                // Cancelled
                isDone = true;
                ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
            }
        }

        if ((this.duration < 1.0F) && (!this.openedScreen)) {
            this.openedScreen = true;

            CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : CollectorCollection.collection.group) {
                c.update();
                cg.addToBottom(c);
            }

            AbstractDungeon.overlayMenu.cancelButton.show(GridCardSelectScreen.TEXT[1]);
            AbstractDungeon.gridSelectScreen.open(cg, 1, TEXT[3], false, false, true, true);

        }


        if (this.duration < 0.0F) {
            this.isDone = true;
            if (com.megacrit.cardcrawl.rooms.CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }

    }


    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }

    public void dispose() {
    }
}
