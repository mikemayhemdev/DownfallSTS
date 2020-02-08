package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;

public class DiscoverEtherealAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private int count;

    public DiscoverEtherealAction(int count) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.count = count;
    }

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
                        if (AbstractDungeon.player.hand.size() < 10) {
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
    }// 29 54

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();

        while (derp.size() != 3) {
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(99);
            AbstractCard.CardRarity cardRarity;
            if (roll < 55) {
                cardRarity = AbstractCard.CardRarity.COMMON;
            } else if (roll < 85) {
                cardRarity = AbstractCard.CardRarity.UNCOMMON;
            } else {
                cardRarity = AbstractCard.CardRarity.RARE;
            }


            ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
            possList.removeIf(c -> (c.isEthereal == false || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || c.hasTag(AbstractCard.CardTags.HEALING)));

            AbstractCard tmp = possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();

            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }
}
