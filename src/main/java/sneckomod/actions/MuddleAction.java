package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;

public class MuddleAction extends AbstractGameAction {

    private AbstractCard card;

    public MuddleAction(AbstractCard bruhCard) {
        card = bruhCard;
    }

    public void update() {
        isDone = true;
        card.superFlash();
        if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {// 32
            int newCost = AbstractDungeon.cardRandomRng.random(3);// 33
            if (card.cost != newCost) {// 34
                card.cost = newCost;// 35
                card.costForTurn = card.cost;// 36
                card.isCostModified = true;// 37
            }

            card.freeToPlayOnce = false;// 39
        }
    }
}
