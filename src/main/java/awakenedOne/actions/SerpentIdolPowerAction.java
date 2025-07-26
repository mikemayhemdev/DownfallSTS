package awakenedOne.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import expansioncontent.patches.CardColorEnumPatch;
import slimebound.actions.MakeTempCardInHandActionReduceCost;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static awakenedOne.util.Wiz.atb;

public class SerpentIdolPowerAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private int count;

    public SerpentIdolPowerAction(int count) {
        this.actionType = ActionType.CARD_MANIPULATION;// 19
        this.duration = Settings.ACTION_DUR_FAST;// 20
        this.count = count;
    }// 22

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);// 27
            this.tickDuration();// 28
        } else {
            if (!this.retrieveCard) {// 32
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {// 33
                        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();// 34
                        disCard.setCostForTurn(0);// 36
                        disCard.current_x = -1000.0F * Settings.scale;// 38
                        atb(new MakeTempCardInHandAction(disCard, this.amount));
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
            int roll = AbstractDungeon.potionRng.random(99);// 64
            CardRarity cardRarity;
            if (roll < 55) {// 65
                cardRarity = CardRarity.COMMON;// 66
            } else if (roll < 85) {// 67
                cardRarity = CardRarity.UNCOMMON;// 68
            } else {
                cardRarity = CardRarity.RARE;// 70
            }

            AbstractCard tmp = SneckoMod.getOffClassCardMatchingPredicatePotionRng(c -> c.type == AbstractCard.CardType.POWER && !c.hasTag(SneckoMod.BANNEDFORSNECKO) && c.color != AbstractCard.CardColor.COLORLESS && c.color != CardColorEnumPatch.CardColorPatch.BOSS);

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