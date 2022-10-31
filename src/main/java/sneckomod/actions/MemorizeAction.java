//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import slimebound.actions.MakeTempCardInHandActionReduceCost;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.cards.unknowns.Unknown;
import sneckomod.patches.UnknownExtraUiPatch;

import java.util.ArrayList;

public class MemorizeAction extends AbstractGameAction {
    private static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(SneckoMod.makeID("Memorize")).EXTENDED_DESCRIPTION;
    private AbstractPlayer p;

    private ArrayList<AbstractCard> cannotMemorize = new ArrayList<>();

    public MemorizeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;// 22
        this.p = AbstractDungeon.player;// 23
        this.duration = Settings.ACTION_DUR_FAST;// 24
    }// 26

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 30

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                c.update();
                if (UnknownExtraUiPatch.parentCard.get(c) == null) {
                    cannotMemorize.add(c);
                }
            }
            for (AbstractCard c : cannotMemorize) {
                p.hand.group.remove(c);
            }


            if (this.p.hand.group.size() >= 1) {// 74
                AbstractDungeon.handCardSelectScreen.open(EXTENDED_DESCRIPTION[0], 1, false, false);// 75
                this.tickDuration();// 76
                return;// 77
            }

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractCard c3 = c.makeCopy();
                for (AbstractCard c2 : p.masterDeck.group) {
                    if (c2.uuid == UnknownExtraUiPatch.parentCard.get(c).uuid) {
                        if (c2.upgraded) c3.upgrade();
                        p.masterDeck.removeCard(c2);
                        break;
                    }
                }


                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(c3, (float) Settings.WIDTH * .75F, (float) Settings.HEIGHT / 2.0F));

                    AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                            (AbstractCard) UnknownExtraUiPatch.parentCard.get(c), com.megacrit.cardcrawl.core.Settings.WIDTH * 0.35F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));


                    p.hand.addToTop(c);// 106

                    UnknownExtraUiPatch.parentCard.set(c, null);
                }

                this.returnCards();// 95
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
                this.isDone = true;// 98
            }
            this.tickDuration();// 101
        }// 102

        private void returnCards () {
            for (AbstractCard c : cannotMemorize) {
                this.p.hand.addToTop(c);// 106
            }
            this.p.hand.refreshHandLayout();// 108
        }// 109

    }
