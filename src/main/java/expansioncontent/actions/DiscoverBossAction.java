package expansioncontent.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;
import java.util.Collections;

public class DiscoverBossAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private final int count;

    public DiscoverBossAction(int count) {
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
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        ArrayList<AbstractCard> selectionsList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.rarity != AbstractCard.CardRarity.SPECIAL && q.hasTag(expansionContentMod.STUDY)) {
                AbstractCard r = q.makeCopy();
                cardsList.add(r);
            }
        }
        Collections.shuffle(cardsList, AbstractDungeon.cardRandomRng.random);
        for (int i = 0; i < 3; i++) {
            selectionsList.add(cardsList.get(i));
        }

        return selectionsList;
    }
}
