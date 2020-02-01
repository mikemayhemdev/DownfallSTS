package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;

public class MuddleHandAction extends AbstractGameAction {
    private AbstractPlayer p;

    public MuddleHandAction() {
        this.actionType = ActionType.CARD_MANIPULATION;// 14
        this.p = AbstractDungeon.player;// 15
        this.duration = Settings.ACTION_DUR_FAST;// 16
    }// 17

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 21

            for (AbstractCard card : this.p.hand.group) {
                if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {// 24
                    int newCost = AbstractDungeon.cardRandomRng.random(3);// 25
                    if (card.cost != newCost) {// 26
                        card.cost = newCost;// 27
                        card.costForTurn = card.cost;// 28
                        card.isCostModified = true;// 29
                    }
                }
            }

            this.isDone = true;// 33
        } else {
            this.tickDuration();// 38
        }
    }// 34 39
}
