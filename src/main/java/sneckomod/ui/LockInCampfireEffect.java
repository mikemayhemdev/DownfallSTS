package sneckomod.ui;

import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Graphics;
/*     */ import com.badlogic.gdx.graphics.Color;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.math.Interpolation;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.core.Settings;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
/*     */ import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
/*     */ import com.megacrit.cardcrawl.localization.UIStrings;
/*     */ import com.megacrit.cardcrawl.metrics.MetricData;
/*     */ import com.megacrit.cardcrawl.relics.AbstractRelic;
/*     */ import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
/*     */ import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
/*     */ import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
/*     */ import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
/*     */ import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.patches.UnknownExtraUiPatch;
import sneckomod.relics.SuperSneckoSoul;
/*     */ import java.util.ArrayList;

public class LockInCampfireEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("sneckomod:LockInBonfireOptions");
    public static final String[] TEXT = uiStrings.TEXT;

    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();


    public LockInCampfireEffect() {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }


        if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {

                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * .75F, (float) Settings.HEIGHT / 2.0F));

                AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                        (AbstractCard) UnknownExtraUiPatch.parentCard.get(c), com.megacrit.cardcrawl.core.Settings.WIDTH * 0.35F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));


                AbstractDungeon.player.masterDeck.removeCard(UnknownExtraUiPatch.parentCard.get(c));

            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
        }


        if ((this.duration < 1.0F) && (!this.openedScreen)) {
            this.openedScreen = true;

            CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                c.update();
                AbstractCard c2;
                if (c instanceof AbstractUnknownCard){
                    if (((AbstractUnknownCard) c).myList().size() > 0) {
                        c2 = CardLibrary.getCard(((AbstractUnknownCard) c).myList().get(AbstractDungeon.cardRng.random(0, ((AbstractUnknownCard) c).myList().size() - 1))).makeCopy();
                    } else {
                        c2 = CardLibrary.getCard(Madness.ID).makeCopy();
                    }

                    if (c.upgraded) c2.upgrade();
                    cg.addToBottom(c2);
                    UnknownExtraUiPatch.parentCard.set(c2, (AbstractUnknownCard)c);

                }
            }


            AbstractDungeon.gridSelectScreen.open(cg, 1, TEXT[3], false, false, false, false);

        }


        if (this.duration < 0.0F) {
            this.isDone = true;
            if (com.megacrit.cardcrawl.rooms.CampfireUI.hidden) {
                com.megacrit.cardcrawl.rooms.AbstractRoom.waitTimer = 0.0F;
                if (AbstractDungeon.player.hasRelic(SuperSneckoSoul.ID)){

                    ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                } else {
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                }
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
