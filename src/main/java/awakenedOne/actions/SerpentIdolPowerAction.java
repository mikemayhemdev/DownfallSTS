package awakenedOne.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import expansioncontent.patches.CardColorEnumPatch;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SerpentIdolPowerAction extends AbstractGameAction {
    private final int count;
    private boolean retrieveCard = false;

    public SerpentIdolPowerAction(int count) {
        this.actionType = ActionType.CARD_MANIPULATION;// 19
        this.duration = Settings.ACTION_DUR_FAST;// 20
        this.count = count;
    }// 22

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    for (int i = 0; i < this.count; i++) {


                        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                        disCard.setCostForTurn(0);

                        disCard.current_x = -1000.0F * Settings.scale;
                        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        }

                    }
                }

                AbstractDungeon.cardRewardScreen.discoveryCard = null;

                this.retrieveCard = true;
            }
            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();// 57

        while (derp.size() != 3) {// 60
            boolean dupe = false;// 61

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