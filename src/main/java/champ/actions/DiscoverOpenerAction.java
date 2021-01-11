package champ.actions;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class DiscoverOpenerAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    public DiscoverOpenerAction(int copies) {
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_FAST;// 21
        this.amount = copies;// 22
    }// 23

    public void update() {
        ArrayList generatedCards;
        generatedCards = this.generateColorlessCardChoices();// 43

        if (this.duration == Settings.ACTION_DUR_FAST) {// 48
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], false);// 49
            this.tickDuration();// 53
        } else {
            if (!this.retrieveCard) {// 57
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {// 58
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();// 59
                    AbstractCard disCard2 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();// 60
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {// 62
                        disCard.upgrade();// 63
                        disCard2.upgrade();// 64
                    }

                    disCard.setCostForTurn(0);// 67
                    disCard2.setCostForTurn(0);// 68
                    disCard.current_x = -1000.0F * Settings.scale;// 70
                    disCard2.current_x = -1000.0F * Settings.scale + AbstractCard.IMG_HEIGHT_S;// 71
                    if (this.amount == 1) {// 73
                        if (AbstractDungeon.player.hand.size() < 10) {// 74
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 75
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 78
                        }
                    } else if (AbstractDungeon.player.hand.size() + this.amount <= 10) {// 83
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 84
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 89
                    } else if (AbstractDungeon.player.hand.size() == 9) {// 95
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 96
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 101
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 107
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 112
                    }


                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateColorlessCardChoices() {
        ArrayList<AbstractCard> choiceList = new ArrayList<>();
        ArrayList<AbstractCard> bersOpenersList = new ArrayList<>();
        ArrayList<AbstractCard> defOpenersList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.hasTag(ChampMod.OPENERBERSERKER)) bersOpenersList.add(q);
            if (q.hasTag(ChampMod.OPENERDEFENSIVE)) defOpenersList.add(q);
        }
        choiceList.add(bersOpenersList.get(AbstractDungeon.cardRandomRng.random(bersOpenersList.size() - 1)));
        choiceList.add(defOpenersList.get(AbstractDungeon.cardRandomRng.random(defOpenersList.size() - 1)));
        return choiceList;
    }
}
