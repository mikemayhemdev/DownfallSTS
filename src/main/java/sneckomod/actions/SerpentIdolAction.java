package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SerpentIdolAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    public SerpentIdolAction() {
        this.actionType = ActionType.CARD_MANIPULATION;// 19
        this.duration = Settings.ACTION_DUR_FAST;// 20
    }// 22

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);// 27
            this.tickDuration();// 28
        } else {
            if (!this.retrieveCard) {// 32
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {// 33
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();// 34
                    disCard.setCostForTurn(0);// 36

                    disCard.current_x = -1000.0F * Settings.scale;// 38
                    if (AbstractDungeon.player.hand.size() < 10) {// 39
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 40
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 44
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;// 48
                }

                this.retrieveCard = true;// 50
            }

            this.tickDuration();// 53
        }
    }// 29 54

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();// 57

        while (derp.size() != 3) {// 60
            boolean dupe = false;// 61
            int roll = AbstractDungeon.cardRandomRng.random(99);// 64
            CardRarity cardRarity;
            if (roll < 55) {// 65
                cardRarity = CardRarity.COMMON;// 66
            } else if (roll < 85) {// 67
                cardRarity = CardRarity.UNCOMMON;// 68
            } else {
                cardRarity = CardRarity.RARE;// 70
            }

            AbstractCard tmp = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == cardRarity);

            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {// 76
                    dupe = true;// 77
                    break;// 78
                }
            }

            if (!dupe) {// 82
                derp.add(tmp.makeCopy());// 83
            }
        }

        return derp;// 87
    }
}
