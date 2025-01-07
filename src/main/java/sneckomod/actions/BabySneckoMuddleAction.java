package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class BabySneckoMuddleAction extends AbstractGameAction {

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = new ArrayList<>();

        // Filter valid cards based on cost and tags
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.cost <= 0 || card.hasTag(SneckoMod.SNEKPROOF) || card.cost != card.costForTurn) {
                continue; // Skip invalid cards
            }
            validCards.add(card);
        }

        // If there are valid cards, muddle one at random
        if (!validCards.isEmpty()) {
            AbstractCard randomCard = validCards.get(AbstractDungeon.cardRandomRng.random(validCards.size() - 1));
            AbstractDungeon.actionManager.addToBottom(new MuddleAction(randomCard));
        }

        // Mark the action as complete
        this.isDone = true;
    }
}